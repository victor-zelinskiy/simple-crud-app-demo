<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html ng-app="crudApp">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>JavaRush тестовое задание</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/bower_components/bootstrap-css-only/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/bower_components/angular-ui-grid/ui-grid.min.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/css/main.css"/>
    <script src="${pageContext.request.contextPath}/resources/bower_components/tv4/tv4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bower_components/angular/angular.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bower_components/angular-resource/angular-resource.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bower_components/angular-animate/angular-animate.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bower_components/angular-sanitize/angular-sanitize.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bower_components/angular-ui-grid/ui-grid.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bower_components/objectpath/lib/ObjectPath.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bower_components/angular-schema-form/dist/schema-form.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bower_components/angular-schema-form/dist/bootstrap-decorator.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/app/main/app.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/app/controllers/user-controller.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/app/controllers/modal-controller.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/app/services/user-rest-service.js"></script>
    <script type="text/javascript">
        var editModalPath = '${pageContext.request.contextPath}/resources/html/edit-modal-template.html';
    </script>
</head>
<body>
<div class="container" ng-controller="userCtrl" ng-init="getPage(currentPage, itemsPerPage)" id="mainDiv">
    <div class="page-header">
        <h1>User CRUD тестовое приложение</h1>
    </div>
    <form class="form-horizontal">
        <div class="form-group">
            <label for="sizeSelector" class="col-sm-3 control-label">Количество записей на странице:</label>

            <div class="col-sm-1">
                <select id="sizeSelector" class="form-control input-sm" ng-change="pageChanged()"
                        ng-model="itemsPerPage" ng-options="opt for opt in options.pageSizes"></select>
            </div>
            <button type="button" class="btn btn-info" ng-disabled="isAddBtnDisable" ng-click="addUser()">Добавить пользователя</button>
            <button type="button" class="btn btn-info" ng-disabled="isDelBtnDisable" ng-click="deleteUsers()">Удалить выбранных
                пользователей
            </button>
        </div>
    </form>

    <div class="pagination-div">
        <pagination boundary-links="true" total-items="totalItems" ng-model="currentPage"
                    items-per-page="itemsPerPage" ng-change="pageChanged()" max-size="26" class="pagination-sm"
                    previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;"
                    last-text="&raquo;"></pagination>
    </div>

    <div ui-grid="gridUsers" ui-grid-pagination ui-grid-edit ui-grid-selection class="grid" ui-grid-auto-resize ng-style="getTableHeight()">
    </div>
    <div class="pagination-div">
        <pagination boundary-links="true" total-items="totalItems" ng-model="currentPage"
                    items-per-page="itemsPerPage" ng-change="pageChanged()" max-size="26" class="pagination-sm"
                    previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;"
                    last-text="&raquo;"></pagination>
        <div class="watermark" ng-show="!gridUsers.data.length">В БД нет пользователей удовлетворяющих запросу</div>
    </div>

</div>
</body>
</html>