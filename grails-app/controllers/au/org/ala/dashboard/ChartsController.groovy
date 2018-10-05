package au.org.ala.dashboard

class ChartsController {

    def MetadataService metadataService

    def collections() {
        Map collections = metadataService.getCollectionsByCategory()
        def columns = [['string', 'category'], ['number', 'collections']]
        def data = [
                ['Plants', collections.plants],
                ['Insects', collections.insects],
                ['Other fauna', collections.otherFauna],
                ['Microbes', collections.micro]
        ]

        [columns: columns, data: data]
    }

    def stateAndTerritoryRecords() {
        List stateAndTerritoryRecords = metadataService.getStateAndTerritoryRecords()
        def columns = [['string', 'state'], ['number', 'records']]
        def data = stateAndTerritoryRecords.collect { record ->
            [record.label, record.count]
        }

        [columns: columns, data: data]
    }

    def recordsAndSpeciesByDecade = {
        List recordsAndSpeciesByDecade = metadataService.getSpeciesByDecade()
        def columns = [['string', 'Decade'], ['number', 'Records'], ['number', 'Species']]
        def data =recordsAndSpeciesByDecade.collect {[it.decade, it.records, it.species]}

        [columns: columns, data: data]
    }

    def openAndSharedLicenceRecords() {
        Map openAndSharedLicences = metadataService.getRecordsByLicenceType()
        def columns = [['string', 'Licence'], ['number', 'Records']]
        def data = [
                ['Open', openAndSharedLicences.open],
                ['Shared', openAndSharedLicences.shared]
        ]

        [columns: columns, data: data]
    }

    def openAndSharedLicenceDataResources() {
        Map openAndSharedLicences = metadataService.getDataResourceByLicenceType()
        def columns = [['string', 'Licence'], ['number', 'Data resources']]
        def data = [
                ['Open', openAndSharedLicences.open],
                ['Shared', openAndSharedLicences.shared]
        ]

        [columns: columns, data: data]
    }

    def coordinateUncertaintyRecords() {
        Map coordinateUncertaintyRecords = metadataService.getRecordsByCoordinateUncertainty()
        def columns = [['string', 'Resolution'], ['number', 'Records']]
        def data = coordinateUncertaintyRecords.collect { record ->
            [record.getKey(), record.getValue()]
        }

        [columns: columns, data: data]
    }

    def identificationVerificationRecords() {
        Map identificationVerification = metadataService.getRecordsByIdentificationVerificationStatus()
        def columns = [['string', 'Status'], ['number', 'Records']]
        def data = identificationVerification.facets.collect { facet ->
            [facet.display, facet.count]
        }

        [columns: columns, data: data]
    }
}
