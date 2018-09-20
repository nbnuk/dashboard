<div class="col-sm-4 col-md-4" id="records-topic">
    <div class="panel">
        <div class="panel-heading">
            <div class="panel-title">Occurrence records<i class="fa fa-info-circle pull-right hidden"></i>
            </div>
        </div>
        <div class="panel-body">
            <table class="table table-condensed table-striped table-hover link">
                <tr><td>Total records</td>
                    <td class="numberColumn">
                        <span class="count"><db:addCommas value="${recordStats.totalRecords}"/></span>
                    </td>
                </tr>
                <tr><td>Species with records</td>
                    <td class="numberColumn">
                        <span class="count"><db:addCommas value="${recordStats.speciesWithRecords}"/></span>
                    </td>
                </tr>
                <tr><td>Latest occurrence</td>
                    <td class="numberColumn"><span class="count">
                        <a href="${grailsApplication.config.records.baseURL}/occurrences/${recordStats.latest.uuid}">
                            ${recordStats.latest.display}
                        </a>
                        </span></td>
                <tr><td id="${recordStats.latestImage.uuid}">Latest image</td>
                    <td class="numberColumn"><span class="count">
                        <a href="${grailsApplication.config.records.baseURL}/occurrences/${recordStats.latestImage.uuid}">
                            ${recordStats.latestImage.display}
                        </a>
                        </span>
                    </td>
                 </tr>
             </table>
        </div>
    </div>
</div>