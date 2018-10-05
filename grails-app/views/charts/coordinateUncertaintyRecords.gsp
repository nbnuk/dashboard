<div id="coordinateUncertainty-chart"></div>
<g:if test="${!data}">
    <asset:image src="spinner.gif" />
</g:if>
<gvisualization:columnCoreChart
        name="coordinateUncertainty"
        dynamicLoading="${true}"
        elementId="coordinateUncertainty-chart"
        title=""
        columns="${columns}"
        data="${data}"
        height="250"
        vAxes="${[0: [logScale: true]]}"
        chartArea="${[left: 85, width: '80%']}"
        legend=""
/>