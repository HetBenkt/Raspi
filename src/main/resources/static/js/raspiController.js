/**
 * Created by bosa on 23-8-2017.
 * Angular controller for the UI app
 */
app.controller("raspiController", function ($scope, $http) {
    var currentUrl = window.location.href;
    console.log(currentUrl);
    $scope.history = new Date().toLocaleString() + ': App started...';

    $scope.testApi = function () {
        $http.get(currentUrl + "api/test")
            .then(function (response) {
                $scope.history = new Date().toLocaleString() + ': ' + response.data.message + '\n' + $scope.history;
            })
    };

    $scope.initGpio = function () {
        $http.get(currentUrl + "api/gpio/init")
            .then(function (response) {
                $scope.history = new Date().toLocaleString() + ': ' + response.data.message + '\n' + $scope.history;
            })
            .catch(function (error) {
                console.log(error);
                $scope.history = new Date().toLocaleString() + ': ' + error.data.error + '\n' + $scope.history;
            })
    };

    $scope.shutdownGpio = function () {
        $http.get(currentUrl + "api/gpio/off")
            .then(function (response) {
                $scope.history = new Date().toLocaleString() + ': ' + response.data.message + '\n' + $scope.history;
            })
            .catch(function (error) {
                console.log(error);
                $scope.history = new Date().toLocaleString() + ': ' + error.data.error + '\n' + $scope.history;
            })
    };

    $scope.ledOn = function () {
        $http.get(currentUrl + "api/led/on")
            .then(function (response) {
                $scope.history = new Date().toLocaleString() + ': ' + response.data.message + '\n' + $scope.history;
            })
            .catch(function (error) {
                console.log(error);
                $scope.history = new Date().toLocaleString() + ': ' + error.data.error + '\n' + $scope.history;
            })
    };

    $scope.ledOff = function () {
        $http.get(currentUrl + "api/led/off")
            .then(function (response) {
                $scope.history = new Date().toLocaleString() + ': ' + response.data.message + '\n' + $scope.history;
            })
            .catch(function (error) {
                console.log(error);
                $scope.history = new Date().toLocaleString() + ': ' + error.data.error + '\n' + $scope.history;
            })
    };

});