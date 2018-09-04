package au.org.ala.dashboard

import grails.converters.JSON

import static grails.async.Promises.task

/**
 * Handles caching of service responses (after transforming).
 * Uses passed closures to handle service requests - so remains independent
 * of the source of information.
 * Implements the info source for 'static' counts read from a config file.
 */
class CacheService {

    static cache = [:]

    def grailsApplication

    /**
     * Returns the cached results for the specified key if available and fresh
     * else calls the passed closure to get the results (and cache them).
     * @param key for cache storage
     * @param source closure to retrieve the results if required
     * @param maxAgeInDays the maximum age of the cached results
     * @return the results
     */
    def get(String key, Closure source, int maxAgeInDays = 5) {
        def cached = cache[key]
        def results
        //log.info "max age = ${maxAgeInDays}" // RR was 1
        log.info "cache[${key}] = ${cached}"
        //refreshCache(key, cache, source) // RR to force-populate cache
        if (cached?.resp && !(new Date().after(cached?.time + maxAgeInDays))) {
            log.info "using cache for ${key}"
            results = cached.resp
        } else if (cached?.resp && new Date().after(cached?.time + maxAgeInDays)) {
            log.info "cached result for ${key} has expired"
            // This prevents new user from refreshing the cache while it is happening in the background
            cached.time = new Date()
            // We trigger the cache refresh for this particular key in a separate thread
            def refreshTask = task {
                refreshCache(key, cache, source)
            }

            refreshTask.onComplete {
                log.info("Cache entry with key ${key} has been refreshed")
            }
            // We return the current cached value which probably is not the new one for the current request
            results = cached.resp
        } else {
            log.info "using source to get data: ${source}"
            try {
                log.info "retrieving " + key
                results = source.call()
                if (!results || (results instanceof Map && results.errorCode)) {
                    clear key
                } else {
                    cache.put key, [resp: results, time: new Date()]
                    log.info "cached ${key} -> ${results}"
                }
            } catch (e) {
                log.info '(in get)'
                log.info "There was a problem retrieving the dashboard data for key ${key}: ${e.message}"
                clear key
            }
        }

        return results
    }

    def clear(key) {
        cache[key]?.resp = null
    }

    def clear() {
        cache = [:]
    }

    /**
     * Info provider based on an external metadata file.
     * Loading any key will load results for all keys in the file.
     * @param key the type of info requested
     * @return
     */
    def loadStaticCacheFromFile(key) {
        log.info "loading static data from file for ${key}"

        def json = new File(grailsApplication.config.dashboard.data.file as String).text
        if (json) {
            JSON.parse(json).each { k,v ->
                cache.put k, [resp: v, time: new Date()]
                log.info "cached key = ${k}, value = ${v}"
            }
        }
        return cache[key]?.resp
    }

    def refreshCache(key, cache, source) {
        log.info("Adding ${key} key to cache")
        try {
            def value = source.call()
            if (!value || (value instanceof Map && value.errorCode)) {
                clear key
            } else {
                cache.put(key, [resp: value, time: new Date()])
                log.info "I have cached ${key} = ${value}"
            }
        } catch (e) {
            log.info "There was a problem retrieving the dashboard data for key ${key}: ${e.message}"
        }
    }
}



