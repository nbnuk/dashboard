<div class="col-sm-4 col-md-4" id="datasets-topic">
    <div class="panel">
        <div class="panel-heading">
            <div class="panel-title">
                Data resources
                <i class="fa fa-info-circle pull-right hidden"></i>
            </div>
        </div>
        <div class="panel-body">
            <table class="table table-condensed table-striped table-hover">
                 <tr class="link">
                    <td id="records">Data Resources</td>
                    <td class="numberColumn"><span class="count"><db:formatNumber
                            value="${datasets.groups.records}"/></span>
                    </td>
                </tr>
                <tr class="link">
                    <td class="text-indent" id="dataAvailable">&#8226; Data Available</td>
                    <td class="numberColumn"><span class="count"><db:formatNumber
                            value="${datasets.dataAvailableCount}"/></span>
                    </td>
                </tr>
                <tr>
                    <td class="text-indent" id="description">&#8226; Description only</td>
                    <td class="numberColumn"><span class="count"><db:formatNumber
                            value="${datasets.descriptionOnlyCount}"/></span>
                    </td>
                </tr>
             </table>

            <p class="paragraph">
                Most recently added data resource is:<br/>
            </p>
            <div class="text-center">
                <a href="${grailsApplication.config.collectory.baseURL}/public/show/${datasets.last.uid}">
                    <db:shorten text="${datasets.last.name}" size="100"/>
                </a>
            </div>
        </div>
    </div>
</div>