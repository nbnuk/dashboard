<div id="collections-chart"></div>
<gvisualization:pieCoreChart
        name="collections"
        dynamicLoading="${true}"
        elementId="collections-chart"
        title=""
        columns="${columns}"
        data="${data}"
        is3D="${true}"
        legend="none"
        pieSliceText="label" chartArea="${new Expando(width: '100%', height: '100%')}"
        pieSliceTextStyle="${new Expando(fontSize: '12')}"
        backgroundColor="${new Expando(fill: 'transparent')}"
        select="function() {dashboard.charts.collection.showCollection(collections, collections_data)}"
        onmouseover="function() { jQuery('#collections-chart').css('cursor','pointer')}"
        onmouseout="function() { jQuery('#collections-chart').css('cursor','default')}"/>