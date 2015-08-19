(function () {
    angular.module('crudApp.services')
        .factory('userFactory', ['$http', '$log', function ($http, $log) {

            var urlBase = './crud';
            var userFactory = {};

            userFactory.getAllUsers = function () {
                return $http.get(urlBase + '/all');
            };

            userFactory.getUser = function (id) {
                return $http.get(urlBase + '/getById/' + id);
            };

            userFactory.getPage = function (pageNumber, itemsPerPage, sortType, sortField, nameFilter, ageMoreFilter, ageLessFilter, isAdminFilter) {
                var urlFull = urlBase + '/getPage/' + itemsPerPage + '/' + pageNumber;
                var restParams =  {};
                var data = {};
                if ((sortType != null) && (sortField != null)) {
                    restParams['sortType'] = sortType;
                    restParams['sortField'] = sortField;
                }

                if (nameFilter != null) {
                    restParams['nameFilter'] = nameFilter;
                }
                if (isAdminFilter != null) {
                    restParams['isAdminFilter'] = isAdminFilter;
                }
                if (ageMoreFilter != null) {
                    restParams['ageMoreFilter'] = ageMoreFilter;
                }
                if (ageLessFilter != null) {
                    restParams['ageLessFilter'] = ageLessFilter;
                }

                data['params'] = restParams;




                return $http.get(urlFull, data);
            };

            userFactory.deleteUser = function (id) {
                return $http.delete(urlBase + '/delete/' + id);
            };

            userFactory.postUser = function (user) {
                return $http.post(urlBase + '/post', user);
            };


            return userFactory;
        }]);
}());
