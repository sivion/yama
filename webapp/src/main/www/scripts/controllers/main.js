'use strict';

/**
 * @ngdoc function
 * @name yamaApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the yamaApp
 */
angular.module('yamaApp')
  .controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
