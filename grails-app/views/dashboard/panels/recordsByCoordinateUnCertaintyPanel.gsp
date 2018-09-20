<div class="col-sm-4 col-md-4" id="basis-topic">
    <div class="panel">
        <div class="panel-heading">
            <div class="panel-title">Records by resolution<i class="fa fa-info-circle pull-right hidden"></i></div>
        </div>
        <div class="panel-body">
            <table class="table table-condensed table-striped table-hover">
                <tbody>
                        <tr class="link">
                            <td>1m or less</td>
                            <td class="numberColumn">
                                <span class="count">
                                    <db:formatNumber value="${coordinateUncertainty["1m"]}" />
                                </span>
                            </td>
                        </tr>
                        <tr class="link">
                            <td>up to 10m</td>
                            <td class="numberColumn">
                                <span class="count">
                                    <db:formatNumber value="${coordinateUncertainty["10m"]}" />
                                </span>
                            </td>
                        </tr>
                        <tr class="link">
                            <td>up to 100m</td>
                            <td class="numberColumn">
                                <span class="count">
                                    <db:formatNumber value="${coordinateUncertainty["100m"]}" />
                                </span>
                            </td>
                        </tr>
                        <tr class="link">
                            <td>up to 1km</td>
                            <td class="numberColumn">
                                <span class="count">
                                    <db:formatNumber value="${coordinateUncertainty["1km"]}" />
                                </span>
                            </td>
                        </tr>
                        <tr class="link">
                            <td>up to 2km</td>
                            <td class="numberColumn">
                                <span class="count">
                                    <db:formatNumber value="${coordinateUncertainty["2km"]}" />
                                </span>
                            </td>
                        </tr>
                        <tr class="link">
                            <td>up to 10km</td>
                            <td class="numberColumn">
                                <span class="count">
                                    <db:formatNumber value="${coordinateUncertainty["10km"]}" />
                                </span>
                            </td>
                        </tr>
                        <tr class="link">
                            <td>up to 100km</td>
                            <td class="numberColumn">
                                <span class="count">
                                    <db:formatNumber value="${coordinateUncertainty["100km"]}" />
                                </span>
                            </td>
                        </tr>
                 </tbody>
            </table>
        </div>
    </div>
</div>