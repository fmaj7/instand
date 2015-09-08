angular.module('instand.controllers', [])

.controller('AppCtrl', function($scope, $ionicModal, $timeout) {

  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //$scope.$on('$ionicView.enter', function(e) {
  //});

  // Form data for the login modal
  $scope.loginData = {};

  // Create the login modal that we will use later
  $ionicModal.fromTemplateUrl('templates/login.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.modal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeLogin = function() {
    $scope.modal.hide();
  };

  // Open the login modal
  $scope.login = function() {
    $scope.modal.show();
  };

  // Perform the login action when the user submits the login form
  $scope.doLogin = function() {
    console.log('Doing login', $scope.loginData);

    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {
      $scope.closeLogin();
    }, 1000);
  };
})

.controller('BrowseCtrl', function($scope) {
  $scope.topics = [
    {
      id : 1,
      title : "Tight End",
      answers : [
        {
          id : 1,
          userid : 1,
          details : "That Guy",
          imgurl : "http://strongfootballcoach.com/wp-content/uploads/2012/11/vikings-double-tight-end-1.jpg",
        }
      ]
    },
    {
      id : 2,
      title : "Offside in Soccer",
      answers : [
        {
          id : 2,
          userid : 1,
          details : "off to the wrong side"
        }
      ]
    },
    {
      id : 3,
      title : "Offside in Hockey",
      answers : [
        {
          id : 3,
          userid : 2,
          details : "Fastest in linemen, biggest in receivers"
        }
      ]
    }
  ];
})

;
