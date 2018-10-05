<div id="identificationVerification-chart"></div>
<g:if test="${!data}">
    <asset:image src="spinner.gif" />
</g:if>
<gvisualization:columnCoreChart
        name="identificationVerification"
        dynamicLoading="${true}"
        elementId="identificationVerification-chart"
        title=""
        columns="${columns}"
        data="${data}"
        height="450"
        vAxes="${[0: [logScale: true]]}"
        chartArea="${[left: 85, width: '90%']}"
        legend=""
/>