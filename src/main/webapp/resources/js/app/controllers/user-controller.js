(function () {
    angular.module('crudApp.controllers').controller('userCtrl', ['$scope', '$modal', '$log', '$interval', 'uiGridConstants', 'userFactory',
        function ($scope, $modal, $log, $interval, uiGridConstants, userFactory) {
            $scope.totalItems = 0;
            $scope.currentPage = 1;
            $scope.itemsPerPage = 10;
            $scope.sortType = null;
            $scope.sortField = null;
            $scope.nameFilter = null;
            $scope.ageMoreFilter = null;
            $scope.ageLessFilter = null;
            $scope.isAdminFilter = null;
            $scope.isDelBtnDisable = true;
            $scope.isAddBtnDisable = false;
            $scope.options = {
                pageSizes: [10, 25, 50, 100, 200]
            };



            $scope.gridUsers = {
                enablePaginationControls: false,
                enableHorizontalScrollbar: false,
                useExternalSorting: true,
                useExternalFiltering: true,
                enableColumnMenu: false,
                enableExpandable: false,
                enableColumnMenus: false,
                enableSorting: true,
                enablePinning: false,
                multiSelect: true,
                enableFiltering: true,
                paginationPageSize: 10,
                columnDefs: [
                    {
                        field: 'name',
                        displayName: 'Имя'},
                    {
                        field: 'age',
                        displayName: 'Возраст',
                        type: 'number',
                        filters: [
                            {
                                condition: uiGridConstants.filter.GREATER_THAN,
                                placeholder: 'больше чем'
                            },
                            {
                                condition: uiGridConstants.filter.LESS_THAN,
                                placeholder: 'меньше чем'
                            }
                        ]
                    },
                    {
                        field: 'isAdmin',
                        displayName: 'Администратор',
                        cellTemplate: '<div>{{row.entity.isAdmin === true ? "Да" : "Нет"}}</div>',
                        type: 'boolean',
                        filter: {
                            type: uiGridConstants.filter.SELECT,
                            selectOptions: [ { value: true, label: 'Да' }, { value: false, label: 'Нет' }]
                        }
                    },
                    {
                        field: 'createdDate',
                        displayName: 'Дата регистрации',
                        type: 'date',
                        cellFilter: 'date:"dd-MM-yyyy"',
                        enableFiltering: false
                    }
                ]
            };

            $scope.getTableHeight = function () {
                var rowHeight = 30;
                var headerHeight = 110;
                return {
                    height: ($scope.gridUsers.data.length * rowHeight + headerHeight) + "px"
                };
            };


            $scope.gridUsers.onRegisterApi = function (gridApi) {
                gridApi.core.on.sortChanged($scope, $scope.sortChanged);
                $scope.sortChanged(gridApi.grid, [$scope.gridUsers.columnDefs[1]]);
                gridApi.selection.on.rowSelectionChanged($scope, function () {
                    $scope.isDelBtnDisable = gridApi.selection.getSelectedRows().length <= 0;
                });

                gridApi.edit.on.afterCellEdit($scope, function (rowEntity) {
                    userFactory.postUser({
                        'id': rowEntity.id,
                        'name': rowEntity.name,
                        'age': rowEntity.age,
                        'isAdmin': rowEntity.isAdmin,
                        'createdDate': rowEntity.createdDate.getTime()
                    }).then(function () {
                        $scope.getPage($scope.currentPage, $scope.itemsPerPage);
                    }, function (error) {
                        $scope.getPage($scope.currentPage, $scope.itemsPerPage);
                        if (error.status === 400) {
                            alert("Некорректная дата, регистрация должна быть в прошлом");
                        }
                    });
                });

                gridApi.core.on.filterChanged( $scope, function() {
                    var grid = this.grid;
                    $scope.nameFilter = grid.columns[1].filters[0].term;
                    $scope.ageMoreFilter = grid.columns[2].filters[0].term;
                    $scope.ageLessFilter = grid.columns[2].filters[1].term;
                    $scope.isAdminFilter = grid.columns[3].filters[0].term;

                    $scope.getPage($scope.currentPage, $scope.itemsPerPage);

                });
                $scope.gridApi = gridApi;
            };

            $scope.setPage = function (pageNo) {
                $scope.currentPage = pageNo;
            };

            $scope.sortChanged = function (grid, sortColumns) {
                if (sortColumns[0] === undefined || sortColumns[0].sort === undefined) {
                    $scope.sortType = null;
                    $scope.sortField = null;
                } else switch (sortColumns[0].sort.direction) {
                    case uiGridConstants.ASC:
                        $scope.sortType = "asc";
                        $scope.sortField = sortColumns[0].field;
                        break;
                    case uiGridConstants.DESC:
                        $scope.sortType = "desc";
                        $scope.sortField = sortColumns[0].field;
                        break;
                }
                $scope.getPage($scope.currentPage, $scope.itemsPerPage);
            };


            $scope.pageChanged = function () {
                $scope.getPage($scope.currentPage, $scope.itemsPerPage);
            };


            $scope.getPage = function (pageNumber, itemsPerPage) {
                $scope.gridUsers.paginationPageSize = itemsPerPage;
                userFactory.getPage(pageNumber, itemsPerPage, $scope.sortType, $scope.sortField, $scope.nameFilter, $scope.ageMoreFilter, $scope.ageLessFilter, $scope.isAdminFilter).success(function (page) {
                    angular.forEach(page.data, function (row) {
                        row.createdDate = new Date(row.createdDate);
                    });
                    $scope.users = page.data;
                    $scope.totalItems = page.totalUsersNumber;
                    $scope.gridUsers.data = $scope.users;
                });
            };

            $scope.deleteUsers = function () {
                var len = $scope.gridApi.selection.getSelectedRows().length;
                var index = 0;
                angular.forEach($scope.gridApi.selection.getSelectedRows(), function (row) {
                    index++;
                    userFactory.deleteUser(row.id).then(function () {
                        if (index === len) {
                            $scope.getPage($scope.currentPage, $scope.itemsPerPage);
                        }
                    }, function () {
                        if (index === len) {
                            $scope.getPage($scope.currentPage, $scope.itemsPerPage);
                        }
                    });
                });
                $scope.isDelBtnDisable = true;
            };

            $scope.addUser = function () {
                var modalInstance = $modal.open({
                    templateUrl: editModalPath,
                    controller: 'modalCtrl',
                    scope: $scope
                });
                modalInstance.result.then(function(data) {
                    userFactory.postUser({
                        'name': data.name,
                        'age': data.age,
                        'isAdmin': data.isAdmin,
                        'createdDate': new Date().getTime()
                    }).then(function () {
                        $scope.getPage($scope.currentPage, $scope.itemsPerPage);
                    });
                });
            };
        }]);
}());


