<div id="openAndSharedLicenceDataResources-chart"></div>
<g:if test="${!data}">
    <asset:image src="spinner.gif" />
</g:if>
<gvisualization:pieCoreChart
        name="openAndSharedLicenceDataResources"
        dynamicLoading="${true}"
        elementId="openAndSharedLicenceDataResources-chart"
        title="Data resources"
        columns="${columns}"
        data="${data}"
        is3D="${true}"
        legend="none"
        pieSliceText="label" chartArea="${[width: '100%', height: '100%']}"
        pieSliceTextStyle="${[fontSize: '12']}"
        backgroundColor="${[fill: 'transparent']}"
        onmouseover="function() { jQuery('#openAndSharedLicenceDataResources-chart').css('cursor','pointer')}"
        onmouseout="function() { jQuery('#openAndSharedLicenceDataResources-chart').css('cursor','default')}"/>