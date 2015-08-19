angular.module('crudApp.controllers').controller('modalCtrl', function ($scope, $log, $modalInstance) {

    $scope.userSchema = {
        type: 'object',
        properties: {
            name: { type: 'string', title: 'Имя' },
            age: { type: 'integer', title: 'Возраст' },
            isAdmin: { type: 'boolean', title: 'Администратор' }
        },
        required: [
            "name",
            "age"
        ]
    };



    $scope.userForm = [
        {
            key: 'name',
            $validators: {
                validAge: function (value) {
                    if (!angular.isString(value)) {
                        return false;
                    }
                    return value.length > 3;
                }
            },
            validationMessage: {
                'validAge': "Имя должно быть длинее 3-х символов"
            }
        },
        {
            key: 'age',
            $validators: {
                validAge: function (value) {
                    if (!angular.isNumber(value)) {
                        return false;
                    }
                    return !((value < 0) || (value > 100));
                }
            },
            validationMessage: {
                'validAge': "Некорректный возраст"
            }
        },
        {
            "key": "isAdmin"
        }
    ];

    $scope.userModel = {};




    $scope.ok = function () {
        $modalInstance.close($scope.userModel);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss();
    };
});