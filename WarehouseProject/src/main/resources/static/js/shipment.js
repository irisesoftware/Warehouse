$(document).ready(function(){
            //1. hide error section
            $("#shipmentModeError").hide();
            $("#shipmentCodeError").hide();
            $("#enableShipmentError").hide();
            $("#shipmentGradeError").hide();
            $("#shipmentDescriptionError").hide();

            //2. define error variables
            var shipmentModeError = false;
            var shipmentCodeError = false;
            var enableShipmentError = false;
            var shipmentGradeError = false;
            var shipmentDescriptionError = false;

            //3. validate function
            function validate_shipmentMode() {
                var val = $("#shipmentMode").val();
                if(val=='') {
                   $("#shipmentModeError").show(); 
                   $("#shipmentModeError").html("Please choose <b> Shipment Mode</b>");
                   $("#shipmentModeError").css('color','red'); 
                   shipmentModeError = false;
                } else {
                    $("#shipmentModeError").hide(); 
                    shipmentModeError = true;
                }
                return shipmentModeError;
            }

            function validate_shipmentCode() {
                var val = $("#shipmentCode").val();
                var exp =/^[A-Z0-9\.\-]{4,12}$/;
                if(val=='') {
                    $("#shipmentCodeError").show();
                    $("#shipmentCodeError").html("Enter <b>Shipment Code</b>");
                    $("#shipmentCodeError").css('color','red');
                    shipmentCodeError = false;
                } else if(!exp.test(val)) {
                    $("#shipmentCodeError").show();
                    $("#shipmentCodeError").html("Invaild <b>Shipment Code</b>");
                    $("#shipmentCodeError").css('color','red');
                    shipmentCodeError = false;
                } 
                 else {
                    $("#shipmentCodeError").hide();
                    shipmentCodeError = true;
                }
                return shipmentCodeError;
            }

            function validate_enableShipment() {
                var val = $('[name="enableShipment"]:checked').length;
                if(val==0) {
                    $("#enableShipmentError").show();
                    $("#enableShipmentError").html("Please choose one Option");
                    $("#enableShipmentError").css('color','red');
                    enableShipmentError = false;
                } else {
                    $("#enableShipmentError").hide();
                    enableShipmentError = true;
                }
                return enableShipmentError;
            }

            function validate_shipmentGrade() {
                var len = $('[name="shipmentGrade"]:checked').length;
                if(len==0) {
                    $("#shipmentGradeError").show();
                    $("#shipmentGradeError").html("Please choose one Option");
                    $("#shipmentGradeError").css('color','red');
                    shipmentGradeError = false;
                } else {
                    $("#shipmentGradeError").hide();
                    shipmentGradeError = true;
                }
                return shipmentGradeError;
            }

            function validate_shipmentDescription(){
                var val = $("#shipmentDescription").val();
                var exp =/^[A-Za-z0-9\.\,\s\-]{5,150}$/;
                if(val=='') {
                    $("#shipmentDescriptionError").show();
                    $("#shipmentDescriptionError").html("Description <b> can not be empty</b>");
                    $("#shipmentDescriptionError").css('color','red');
                    shipmentDescriptionError = false;
                } else if(!exp.test(val)) {
                    $("#shipmentDescriptionError").show();
                    $("#shipmentDescriptionError").html("Description <b> must bte 5-150 chars only</b>");
                    $("#shipmentDescriptionError").css('color','red');
                    shipmentDescriptionError = false;
                } else {
                    $("#shipmentDescriptionError").hide();
                    shipmentDescriptionError = true;
                }
                return shipmentDescriptionError;
            }

            //4. link with action even
            $("#shipmentMode").change(function(){
               validate_shipmentMode();
            })
            $("#shipmentCode").keyup(function(){
                $(this).val($(this).val().toUpperCase());
                validate_shipmentCode();
            })
            $("[name='enableShipment']").change(function(){
               validate_enableShipment();
            })
            $("[name='shipmentGrade']").change(function(){
                validate_shipmentGrade();
            })
            $("#shipmentDescription").keyup(function(){
                validate_shipmentDescription();
            })

            //5. on form submit
            $("#shipRegForm").submit(function(){
                validate_shipmentMode();
                validate_shipmentCode();
                validate_enableShipment();
                validate_shipmentGrade();
                validate_shipmentDescription();

                if(shipmentModeError && shipmentCodeError 
                && enableShipmentError && shipmentGradeError
                && shipmentDescriptionError ) 
                    return true; //means submit form
                else 
                    return false;//dont  submit form
            });
        });