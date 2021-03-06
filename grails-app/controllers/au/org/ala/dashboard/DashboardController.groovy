/*
 * Copyright (C) 2012 Atlas of Living Australia
 * All Rights Reserved.
 *
 * The contents of this file are subject to the Mozilla Public
 * License Version 1.1 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 */

package au.org.ala.dashboard

import au.com.bytecode.opencsv.CSVWriter
import grails.converters.JSON

class DashboardController {

    def metadataService, cacheService

    /**
     * Show main dashboard page.
     */
    def index = {
        [panelInfo: metadataService.getPanelInfo() as JSON]
    }

    def collectionPanel = {
        render view: 'panels/collectionPanel', model: [collections: metadataService.getCollectionsByCategory()]
    }

    def recordsPanel = {
        render view: 'panels/recordsPanel_nbn', model: [recordStats: metadataService.getRecordStats()]
    }

    def datasetsPanel = {
        render view: 'panels/datasetsPanel_nbn', model: [datasets: metadataService.getDatasets()]
    }

    def basisRecordsPanel = {
        render view: 'panels/basisRecordsPanel', model: [basisOfRecord: metadataService.getBasisOfRecord()]
    }

    def dateRecordsPanel = {
        render view: 'panels/dateRecordsPanel', model: [dateStats: metadataService.getDateStats()]
    }

    def nslPanel = {
        render view: 'panels/nslPanel', model: [taxaCounts: metadataService.getTaxaCounts()]
    }

    def spatialPanel = {
        render view: 'panels/spatialPanel', model: [spatialLayers: metadataService.getSpatialLayers()]
    }

    def statePanel = {
        render view: 'panels/statePanel', model: []
    }

    def identificationVerificationStatusPanel = {
        render view: 'panels/recordsByIdentificationVerificationStatusPanel', model: []
    }

    def licencePanel = {
        render view: 'panels/licencePanel', model: []
    }

    def coordinateUncertaintyPanel = {
        render view: 'panels/recordsByCoordinateUncertaintyPanel', model: []
    }

    def identifyLifePanel = {
        render view: 'panels/identifyLifePanel', model: [identifyLifeCounts: metadataService.getIdentifyLifeCounts()]
    }

    def mostRecordedSpeciesPanel = {
        render view: 'panels/mostRecordedSpeciesPanel', model: [mostRecorded: metadataService.getMostRecordedSpecies('all')]
    }

    def typeSpecimensPanel = {
        render view: 'panels/typeSpecimensPanel', model: [typeCounts: metadataService.getTypeStats()]
    }

    def barcodeOfLifePanel = {
        render view: 'panels/barcodeOfLifePanel', model: [boldCounts: metadataService.getBoldCounts()]
    }

    //RR removed
    //def bhlPanel = {
    //    render view: 'panels/bhlPanel', model: [bhlCounts: metadataService.getBHLCounts()]
    //}

    //def volunteerPortalPanel = {
    //    render view: 'panels/volunteerPortalPanel', model: [volunteerPortalCounts: metadataService.getVolunteerStats()]
    //}

    def conservationStatusPanel = {
        render view: 'panels/conservationStatusPanel', model: [stateConservation: metadataService.getSpeciesByConservationStatus()]
    }

    def recordsByDataProviderPanel = {
        render view: 'panels/recordsByDataProviderPanel', model: [dataProviders: metadataService.getDataProviders()]
    }

    //RR removed
    //def recordsByInstitutionPanel = {
    //    render view: 'panels/recordsByInstitutionPanel', model: [institutions: metadataService.getInstitutions()]
    //}

    def recordsByLifeFormPanel = {
        render view: 'panels/recordsByLifeFormPanel', model: [records: metadataService.getRecordsByLifeForm()]
    }

    def recordsAndSpeciesByDecadePanel = {
        render view: 'panels/recordsAndSpeciesByDecadePanel'
    }

    def occurrenceTreePanel = {
        render view: 'panels/occurrenceTreePanel'
    }

    def usageStatisticsPanel = {
        render view: 'panels/usageStatisticsPanel', model: [loggerTotals: metadataService.getLoggerTotals()]
    }

    def downloadsByReasonPanel = {
        render view: 'panels/downloadsByReasonPanel', model: [loggerReasonBreakdown: metadataService.getLoggerReasonBreakdown()]
    }

    def downloadsBySourcePanel = {
        render view: 'panels/downloadsBySourcePanel', model: [loggerSourceBreakdown: metadataService.getLoggerSourceBreakdown()]
    }

    def downloadsByUserTypePanel = {
        render view: 'panels/downloadsByUserTypePanel', model: [loggerEmailBreakdown: metadataService.getLoggerEmailBreakdown()]
    }

    def speciesImagesPanel = {
        render view: 'panels/speciesImagesPanel', model: [imagesBreakdown: metadataService.getImagesBreakdown()]
    }

    def mostRecorded(String group) {
        def facets = metadataService.getMostRecordedSpecies(group)
        render facets as JSON
    }

    def userPanel = {
        render view: 'panels/userPanel', model: [accounts: metadataService.getUserCounts()]
    }

    /**
     * Do logouts through this app so we can invalidate the session.
     *
     * @param casUrl the url for logging out of cas
     * @param appUrl the url to redirect back to after the logout
     */
    def logout = {
        session.invalidate()
        redirect(url:"${params.casUrl}?url=${params.appUrl}")
    }

    def clearCache() {
        if (params.key) {
            cacheService.clear(params.key)
        }
        else {
            cacheService.clear()
        }
        render 'Done.'
    }

    /* ---------------------------- data services ---------------------------------*/

    def downloadAsCsv = {
        /* build files as csv */

        // by decade
        writeCsvFile('by-decade', metadataService.getSpeciesByDecade().collect {
            [it.decade, it.records, it.species] }, ['Decade','Records','Species'])

        // total + dups
        writeCsvFile('total-records', metadataService.getTotalAndDuplicates().findAll({it.key != 'error'}), [])

        // basis of record
        //writeCsvFile('basis-of-record', facetCount('basis_of_record'), ['basisOfRecord','number of records'])

        // records by state
        recordsBy('state')

        // records by kingdom
        recordsBy('kingdom')

        //recordsBy('state_conservation')

        recordsBy('identification_verification_status')

        recordsBy('license')

        recordsByOtherName('species_group','lifeform')

        writeCsvFile('resolution', metadataService.getRecordsByCoordinateUncertainty().collect { record ->
                    [record.getKey(), record.getValue()] },
                ['resolution (m)','number of records'])

        // collections
        //writeCsvFile('collections', metadataService.getCollectionsByCategory(), ['category','number of collections'])

        // data providers
        writeCsvFile('data-providers', metadataService.getDataProviders().collect {[it.facet, it.count]},
                ['data provider','number of records'])

        // spatial layers
       // def md = metadataService.getSpatialLayers()
       // def spMap = [Total: md.total] + md.groups + md.classification
       // writeCsvFile('spatial-layers', spMap, ['type','number'])

        // datasets
        def ds = metadataService.getDatasets()
        def dsMap = [Total: ds.total] + ds.groups
        writeCsvFile('datasets', dsMap, ['type','number'])

        // records by century
        def rc = metadataService.getDateStats()
        def rcList =
        ['c1600','c1700','c1800','c1900','c2000'].collect { [it, rc[it]] }
        writeCsvFile('records-by-century', rcList, ['century','number'])

        // records for types
        //def ty = metadataService.getTypeStats()
        //def tyList = ty.collectEntries { k,v ->
        //    if (k == 'withImage') {
        //        v.collectEntries { l,w -> [(l + ' (with image)'): w]}
        //    }
        //    else {
         //       ["${k}": v]
         //   }
        //}
        //writeCsvFile('type-status', tyList, ['type status', 'number'])

        // taxa counts
        // SR removed
        //writeCsvFile('names', metadataService.getTaxaCounts(), [])

        // bhl counts
        //RR removed
        //writeCsvFile('biodiversity-heritage-library', metadataService.getBHLCounts(), [])

        // identify life counts
        //writeCsvFile('identify-life', metadataService.getIdentifyLifeCounts(), [])

        // vp counts
        //writeCsvFile('biodiversity-volunteer-portal', metadataService.get('volunteerPortalCounts'), [])

        /* zip files */
        //new AntBuilder().zip(destfile: '/data/dashboard/zip/dashboard.zip', basedir: '/data/dashboard/csv/', includes: '**/*.csv')
        new AntBuilder().zip(destfile: grailsApplication.config.csv.temp.dir + 'dashboard.zip', basedir: grailsApplication.config.csv.temp.dir, includes: '**/*.csv') //RR foce


        /* render zip */
        response.setHeader("Content-disposition", "attachment; filename=dashboard.zip");
        //byte[] zip = new File('/data/dashboard/zip/dashboard.zip').bytes
        byte[] zip = new File(grailsApplication.config.csv.temp.dir + 'dashboard.zip').bytes //RR
        response.contentType = "application/zip"
        response.outputStream << zip
    }

    def writeCsvFile(filename, values, header) {
        File dir = new File(grailsApplication.config.csv.temp.dir)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        new File(dir.absolutePath + '/' + filename + '.csv').withWriter { out ->
            def csv = new CSVWriter(out/*, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER*/)
            if (header) { csv.writeNext(header as String[]) }
            if (values instanceof Map) {
                values.each { k,v ->
                    csv.writeNext([k,v] as String[])
                }
            }
            else if (values instanceof List) {
                values.each {
                    csv.writeNext(it as String[])
                }
            }
        }
    }

    def recordsByOtherName(String facet, String name) {
        def dashed = name.replaceAll('_','-')
        writeCsvFile('records-by-' + dashed, facetCount(facet), [dashed,'number of records'])
    }

    def recordsBy(String facet) {
        recordsByOtherName(facet, facet)
    }

    def most = { group ->
        def m = [:]
        metadataService.getMostRecordedSpecies(group)?.facets?.each() {
            m.put it.name, [count: it.count, common: it.common, lsid: it.facet]
        }
        m
    }

    def facetCount = { facet ->
        def r = [:]
        metadataService.getBiocacheFacet(facet).facets.each {
            r.put it.display, it.count
        }
        r
    }

    def data() {
        
        // build output
        def d = [
                totalRecords: metadataService.getTotalAndDuplicates().findAll({it.key != 'error'}),
                //basisOfRecord: facetCount('basis_of_record'), //sr removed
                //collections: metadataService.getCollectionsByCategory(), //SR removed
                datasets: metadataService.getDatasets(),
                recordsByDataProvider:
                        metadataService.getDataProviders().collectEntries {[it.display, it.count]},
                //recordsByInstitution: //RR removed
                //        metadataService.getInstitutions().collectEntries {[it.display, it.count]},
                recordsByDate: metadataService.getDateStats(),
                recordsByState: facetCount('state'),
                recordsByKingdom: facetCount('kingdom'),
                recordsByIdentificationVerificationStatus: facetCount('identification_verification_status'), //SR added
                recordsByLicence: facetCount('license'), //SR added
                recordsByResolution: metadataService.getRecordsByCoordinateUncertainty(), //SR added
                //recordsByConservationStatus: facetCount('state_conservation'), //SR removed
                byDecade: metadataService.getSpeciesByDecade(),
                recordsByLifeform: facetCount('species_group'),
                //spatialLayers: metadataService.getSpatialLayers(), //SR removed
                //typeCounts: metadataService.getTypeStats(),
                taxaCounts: metadataService.getTaxaCounts(),
                //bhlCounts: metadataService.getBHLCounts(), //RR removed
                //volunteerPortalCounts: metadataService.getVolunteerStats(), //RR removed
                occurrenceDownloadByReason: metadataService.getLoggerReasonBreakdown().collect {["Download Reason": it[0], "Events": it[1].trim(), "Records": it[2].trim()]}]
                //volunteerPortalCounts: metadataService.get('volunteerPortalCounts'),
                //identifyLifeCounts: metadataService.getIdentifyLifeCounts()]
        ['All','Plants','Mammals','Reptiles','Birds','Animals','Arthropods',
         'Fishes','Insects','Amphibians','Bacteria','Fungi'].each {
            d['mostRecorded' + it] = most(it)
        }

        render d as JSON
    }

    /**
     * JSON service to return combined set of data stats/metrics for display on the ALA homepage
     * Includes:
     *  - number of users (now and 1 year ago)
     *  - number of records (now and 1 year ago)
     *  - number of species (now and 1 year ago)
     *  - number of datasets (now and 1 year ago)
     *
     *  example output:
     *  {
     *    "userCounts": { "countNow": 1234, "count1YA": 1200 },
     *    "recordCounts": { "countNow": 123400, "count1YA": 120000 } ,
     *    "speciesCounts": { "countNow": 1234, "count1YA": 1200 }
     *  }
     *
     * @return
     */
    def homePageStats() {
        def combinedCounts = [
                userCounts: metadataService.getUserCounts(),
                recordCounts: metadataService.getRecordCounts(),
                speciesCounts:  metadataService.getSpeciesCounts(),
                datasetCounts: metadataService.getDatasetCounts(),
                downloadCounts: metadataService.getDownloadCounts()
        ]

        render combinedCounts as JSON
    }
    
    /* ---------------------------- test actions ---------------------------------*/
    def spatialLayers = {
        render metadataService.getSpatialLayers() as JSON
    }
    def datasets = {
        render metadataService.getDatasets() as JSON
    }

    def metadata = {
        def method = params.id
        def arg = params.arg
        if (arg) {
            render metadataService."$method"(arg) as JSON
        }
        else {
            render metadataService."$method"() as JSON
        }
    }
}
