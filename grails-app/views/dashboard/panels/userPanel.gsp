<div class="col-sm-4 col-md-4" id="basis-topic">
    <div class="panel">
        <div class="panel-heading">
            <div class="panel-title">User accounts<i class="fa fa-info-circle pull-right hidden"></i></div>
        </div>
        <div class="panel-body">
            The number of activated accounts now and this time last year
            <table class="table table-condensed table-striped table-hover">
                <tbody>
                        <tr class="link">
                            <td>${new Date().format( 'dd MMM yyyy' )}</td>
                            <td class="numberColumn"><span class="count">${accounts.count}</span></td>
                        </tr>
                        <tr class="link">
                            <td>1 year ago</td>
                            <td class="numberColumn"><span class="count">${accounts.count1YA}</span></td>
                        </tr>
                  </tbody>
            </table>
        </div>
    </div>
</div>