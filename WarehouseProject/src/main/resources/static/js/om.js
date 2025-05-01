$(document).ready(function () {
        //1. hide error sections
        $("#orderModeError").hide();
        $("#orderCodeError").hide();
        $("#orderTypeError").hide();
        $("#orderAcptError").hide();
        $("#orderDescError").hide();

        //2. define error variables
        var orderModeError = false;
        var orderCodeError = false;
        var orderTypeError = false;
        var orderAcptError = false;
        var orderDescError = false;

        //3. define validate functions
        function validate_orderMode() {
          var len = $('[name="orderMode"]:checked').length;
          if (len == 0) {
            $("#orderModeError").show();
            $("#orderModeError").html("*Please choose <b> one Mode</b>");
            $("#orderModeError").css("color", "red");
            orderModeError = false;
          } else {
            $("#orderModeError").hide();
            orderModeError = true;
          }

          return orderModeError;
        }

        function validate_orderCode() {
          var val = $("#orderCode").val();
          var exp = /^[A-Z0-9\.\-]{4,14}$/;
          if (val == "") {  
            $("#orderCodeError").show();
            $("#orderCodeError").html("*Enter <b>Order Code</b>");
            $("#orderCodeError").css('color','red');
            orderCodeError = false;
          } else if (!exp.test(val)) {
            $("#orderCodeError").show();
            $("#orderCodeError").html("*Invaild <b>Order Code</b>");
            $("#orderCodeError").css('color','red');
            orderCodeError = false;
          } else {
        	
        	//ajax starts  
			var id = 0; //from register
			if($("#id").val()!=undefined) {
				//from edit
				id = $("#id").val();
			}
			
        	$.ajax({
        		url : 'validateCode',
        		data: { "code": val,"id":id},
        		success:function(respTxt) {
        			if(respTxt!=""){ //error exist
        				 $("#orderCodeError").show();
        		         $("#orderCodeError").html(respTxt);
        		         $("#orderCodeError").css('color','red');
        		         orderCodeError = false;
        			} else { //no error
        				$("#orderCodeError").hide();
        	            orderCodeError = true;
        			}
        		}
        	});
        	//ajax end
        	  
            
          }

          return orderCodeError;
        }

        function validate_orderType() {
          var val = $("#orderType").val();
          if (val == '') {
            $("#orderTypeError").show();
            $("#orderTypeError").html("*Please choose <b> one Type</b>");
            $("#orderTypeError").css("color", "red");
            orderTypeError = false;
          } else {
            $("#orderTypeError").hide();
            orderTypeError = true;
          }
            return orderTypeError;
        }

        function validate_orderAcpt() {
            var len = $("[name='orderAcpt']:checked").length;
            if(len==0) {
                $("#orderAcptError").show();
                $("#orderAcptError").html("*Choose at least<b> one Option</b>");
                $("#orderAcptError").css('color','red');
                orderAcptError = false;
            } else {
                $("#orderAcptError").hide();
                orderAcptError = true;
            }
            return orderAcptError;
        }

        function validate_orderDesc() {
          var val = $("#orderDesc").val();
          var exp = /^[A-Za-z0-9\-\.\s\_\,]{10,120}$/;
          if(val=='') {
            $("#orderDescError").show();
            $("#orderDescError").html("Description <b> can not empty </b>");
            $("#orderDescError").css('color','red');
            orderDescError = false;
          } else if(!exp.test(val)) {
            $("#orderDescError").show();
            $("#orderDescError").html("Description <b> is not valid </b>");
            $("#orderDescError").css('color','red');
            orderDescError = false;
          } else {
            $("#orderDescError").hide();
            orderDescError = true;
          }
          return orderDescError;
        }

        //4. link action events
        $("[name='orderMode']").change(function () {
          validate_orderMode();
        });

        $("#orderCode").keyup(function () {
          $(this).val($(this).val().toUpperCase());
          validate_orderCode();
        });

        $("#orderType").change(function(){
            validate_orderType();
        })

        $("[name='orderAcpt']").change(function(){
            validate_orderAcpt();
        })

        $("#orderDesc").keyup(function(){
           validate_orderDesc();
        })

        //5. on click submit
        $("#orderMethodForm").submit(function () {
          //validate all input again
          validate_orderMode();
          validate_orderCode();
          validate_orderType();
          validate_orderAcpt();
          validate_orderDesc();

          //if all are ture then submit form
          if (orderModeError && orderCodeError 
          && orderTypeError && orderAcptError
          && orderDescError ) return true;
          //else do not submit form
          else return false;
        });
      });