$.fn.api.settings.api = {
			  'get followers' : '/followers/{id}?results={count}',
			  'create user'   : 'http://localhost:9090/login',
			  'add user'      : '/add/{id}',
			  'follow user'   : '/follow/{id}',
			  'search'        : '/search/?query={value}'
			};



$(document)
  .ready(function() {

	  
      $("#One").click(function () {
          alert("Handler for .click() called. {/value}");
      });
      
      $('.checkbox')
      .checkbox()
    ;
      
      $('select.dropdown')
      .dropdown()
    ;      
      
      
      $('.ui.form')
      .form({
        name: {
          identifier  : 'name',
          rules: [
            {
              type   : 'empty',
              prompt : 'Please enter your name'
            }
          ]
        },
        gender: {
          identifier  : 'gender',
          rules: [
            {
              type   : 'empty',
              prompt : 'Please select a gender'
            }
          ]
        },
        username: {
          identifier : 'username',
          rules: [
            {
              type   : 'empty',
              prompt : 'Please enter a username'
            }
          ]
        },
        password: {
          identifier : 'password',
          rules: [
            {
              type   : 'empty',
              prompt : 'Please enter a password'
            },
            {
              type   : 'length[6]',
              prompt : 'Your password must be at least 6 characters'
            }
          ]
        },
        terms: {
          identifier : 'terms',
          rules: [
            {
              type   : 'checked',
              prompt : 'You must agree to the terms and conditions'
            }
          ]
        }
      })
      ;
     
      
  });