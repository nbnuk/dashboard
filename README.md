 # NBN Dashboard Application

 This Grails‐based web application aggregates multiple NBN web services to present an interactive dashboard of biodiversity statistics.

 ## NBN-specific Customisations

This application has been configured to work with the National Biodiversity Network (NBN) Atlas as well as the Atlas of Living Australia (ALA). The NBN Atlas configuration is provided in `grails-app/conf/application.yml` (commented ALA values are also available). To switch to NBN Atlas the following ws observed:

- Update the `skin` section:
  - `orgNameLong`: "National Biodiversity Network"
  - `orgNameShort`: "NBN"
  - `privacyPolicy`: "https://nbnatlas.org/help/nbn-atlas-terms-use/"

- Set the following base URLs to the NBN Atlas services (defaults shown):
  - `biocache.baseURL`: https://records-ws.nbnatlas.org
  - `ala.baseURL`: https://nbnatlas.org
  - `bie.baseURL`: https://species-ws.nbnatlas.org
  - `spatial.baseURL`: https://layers.nbnatlas.org/ws
  - `logger.baseURL`: https://logger.nbnatlas.org
  - `collectory.baseURL`: https://registry.nbnatlas.org
  - `images.baseURL`: https://images.nbnatlas.org
  - `userDetails.baseURL`: https://auth.nbnatlas.org/userdetails/
  - `headerAndFooter.baseURL`: https://layout.nbnatlas.org/uk/commonui-bs2

### NBN-specific Extensions

- Added a new License Type Distribution panel using the endpoint `/ws/dataResource/count/licenseType?public=true` (see `Constants.PARTIAL_URL_COUNT_DATASETS_BY_LICENSE_TYPE`).
- Updated the "Latest Record with Image" query to use `assertions:invalidEventDate` required by NBN Atlas.
- Adjusted several partial URL paths (e.g., removal of `/ws` prefix) to match NBN Atlas service routes.

### NBN-specific Limitations

> Note: Some data services are not currently available in NBN Atlas and the corresponding dashboard panels are disabled:
> - Basis of Record
> - Institutions
> - Biodiversity Heritage Library (BHL) counts
> - Barcode of Life Data Systems (BOLD) counts
> - Volunteer Portal statistics
> - Spatial layers
> - Conservation status breakdowns
> - Images breakdown
> - Data Hub / Collectory panels

 ## Technology Stack

 - Grails 3+/Spring‑Boot web application (Groovy on the JVM)
 - Build & dependency management via Gradle (`build.gradle`, `gradlew`)
 - GSP views, asset‑pipeline (JS/CSS/images), jQuery, Google Charts, Bootstrap 3

 ## Project Layout

 ```text
 grails-app/
 ├── assets/             static JS, CSS, images, vendor libraries
 ├── conf/               application.yml (service endpoints, feature toggles),
 │                      QuartzConfig.groovy (job scheduling), JSONP/CORS filter
 ├── controllers/        DashboardController.groovy (panels),
 │                      ChartsController.groovy (chart data)
 ├── views/              index.gsp + one GSP per “panel” under views/dashboard/panels
 ├── services/           MetadataService.groovy (aggregates ALA services),
 │                      WebService.groovy (HTTP/JSON client),
 │                      CacheService.groovy (in‑memory, async refresh)
 ├── jobs/               RefreshCacheEntryJob.groovy (Quartz cache refresher)
 └── taglib/             DashboardTagLib.groovy (number/text formatting helpers)

 src/main/groovy/au/org/ala/dashboard/
 ├── Constants.groovy    partial URLs for ALA endpoints
 └── CountsDTO.groovy    simple DTO for “counts” endpoints

 grails-app/init/
 └── Application.groovy  (Spring‑Boot main)
     BootStrap.groovy    (register JSON marshallers)

 src/test & src/integration-test
 └── Spock/Geb tests (CacheServiceSpec, MetadataServiceSpec)

 build.gradle, settings.gradle, gradlew, etc.
 ```

 ## Runtime & Data Flow

 - Controllers call `MetadataService` on each request, which:
   - Reads base URLs & feature flags from `application.yml`
   - Uses `CacheService.get(key){ … }` to return cached results or fetch via `WebService` from external ALA JSON endpoints (biocache, collectory, spatial, BIE, logger, images, volunteer, userDetails, etc.)
   - Transforms/facets data (species counts, record totals, type specimens) into maps or DTOs
 - Controllers render GSP panels or serve JSON/JSONP for chart endpoints
 - Quartz job (`RefreshCacheEntryJob`) can be scheduled to asynchronously refresh stale cache entries

 ## Configuration

 - `grails-app/conf/application.yml` includes:
   - `biocache.baseURL`, `collectory.baseURL`, `spatial.baseURL`, etc.
   - Feature toggles (`useVolunteerService`, `useBHLService`, …)
   - Paths for static JSON cache (`dashboard.data.file`) and CSV temp dir
 - External overrides via `dashboard-config.properties/.yml/.groovy` on the filesystem


 ## Building & Running

 ```bash
 # Run the application locally
 ./gradlew bootRun

 # Build a deployable WAR
 ./gradlew bootWar
 # or
 ./gradlew war

 # Run unit and integration tests
 ./gradlew test
 ./gradlew integrationTest
 ```

 ## Key Extension Points

 - **Add a new panel**
   Create a `DashboardController` action, hook into `MetadataService`, add a GSP under `views/dashboard/panels`, and register its metadata on the front end.
 - **Add a new external service**
   Define its `baseURL` in `application.yml`, implement calls in `MetadataService`, and cache responses via `CacheService`.
