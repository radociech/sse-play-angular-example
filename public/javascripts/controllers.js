'use strict';

angular.module('serverMessage.controllers',[])
	.controller('ServerMessageCtrl', function($scope, $http) {
		
		$scope.serverMessage = "defaultMessage";
		console.log('ServerMessageCtrl works!');
		
		$scope.messages = [];
		
		$scope.send = function (message) {
          $http.post("/send", {message: message, channel: "1"});
        };
        
        $scope.addMessage = function (message) {
        	console.log(message);
          $scope.$apply(function () { 
        	  $scope.messages.push(JSON.parse(message.data)); 
          });
          
        };
        
		$scope.listen = function () {
          $scope.feed = new EventSource("/feed/1");
          $scope.feed.addEventListener("message", $scope.addMessage, false);
       };
        $scope.listen();
});
