<div id="openAndSharedLicenceRecords-chart"></div>
<g:if test="${!data}">
    <asset:image src="spinner.gif" />
</g:if>
<gvisualization:pieCoreChart
        name="openAndSharedLicenceRecords"
        dynamicLoading="${true}"
        elementId="openAndSharedLicenceRecords-chart"
        title="Records"
        columns="${columns}"
        data="${data}"
        is3D="${true}"
        legend="none"
        pieSliceText="label" chartArea="${[width: '100%', height: '100%']}"
        pieSliceTextStyle="${[fontSize: '12']}"
        backgroundColor="${[fill: 'transparent']}"
        onmouseover="function() { jQuery('#openAndSharedLicenceRecords-chart').css('cursor','pointer')}"
        onmouseout="function() { jQuery('#openAndSharedLicenceRecords-chart').css('cursor','default')}"/>