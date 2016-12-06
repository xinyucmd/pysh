$(function(){
	// 设置银行的可操作性
	
		$('#payType').change(function(){
			// 还款类型
			var payType = $('#payType').val();
			if(payType=='0'){
				$('#bankNo').attr('disabled','disabled');
				$("#bankNo").attr("value",'');
			}else{
				$('#bankNo').removeAttr('disabled');
			}
			
		})
		
	// 设置保证金充值
	 $('#isPerfAmount').change(function(){
			// 还款类型
			var isPerfAmount = $('#isPerfAmount').val();
			// 保证金额
			var perfAmount = Number($('#perfAmount').val());
			if(isPerfAmount=='0'){
				$('#perfAmount').attr('disabled','disabled');
				var returnAmt = Number($('#returnAmt').val()); 
				// 原始金额加上担保金额
				 $('#returnAmt').val(returnAmt+perfAmount);
			}else{
				$('#perfAmount').removeAttr('disabled');
				// 原始还款金额
				var returnAmt = Number($('#returnAmt').val()); 
				// 原始还款金额减去担保金额
				$('#returnAmt').val(returnAmt-perfAmount);
			}
		})
		
	// 履约保证金改变时,修改还款金额
	$('#perfAmount').change(function(){
		var	privilege,// 优惠金额
			perfAmount,// 保证金额
			tempMoney;//应还本金+应还利息+逾期利息+复利利息-优惠金额
			
			
			privilege = Number($('#privilege').val());
			perfAmount = Number($('#perfAmount').val());
			tempMoney =  Number($('#returnAmt').val());
			// 应还金额=应还本金+应还利息+逾期利息+复利利息-优惠金额-保证金额
			$('#returnAmt').val(tempMoney-perfAmount);    
	})
	
	
	
	// 优惠金额改变时,修改还款金额
	$('#privilege').change(function(){
		var privilege,// 优惠金额
			perfAmount,// 保证金额
			tempMoney;//应还本金+应还利息+逾期利息+复利利息-保证金额
			privilege = Number($('#privilege').val());
			perfAmount = Number($('#perfAmount').val());
			tempMoney = Number($('#returnAmt').val());
			// 应还金额=应还本金+应还利息+逾期利息+复利利息-优惠金额-保证金额
			$('#returnAmt').val(tempMoney-privilege);    
	})
	
	
	// 还款金额限制.不是最后一天还款,必须把当期的的利息还完,最后一天可以还部分利息.利随本清必须还完到当前日期的利息
	$('#returnAmt').change(function(){
		var minReturnAmt,// 不是最后一天的最小还款额
		    returnAmt,// 当前输入的还款金额
		    perfAmount,// 保证金额
			isLastDay;//是否最后一天的标志 0 不是最后一天  1 是最后一天
			
		 	minReturnAmt = Number($('#totalInterest').val());
			returnAmt = Number($('#returnAmt').val());
			perfAmount =  $('#perfAmount').val();
			isLastDay = $('#isLastDay').val();
			if($('#isPerfAmount').val()=='0'){
				perfAmount = "0.00";
			}
			perfAmount =  Number(perfAmount);
			// 不是该期的最后一天还款,最小还款金额(还款额+担保金额)不能小于利息和逾期利息及复利利息的和.
			if(returnAmt+perfAmount<minReturnAmt && isLastDay=='0'){
				alert('还款金额过小!');
			}
	}) 
	
		
})

// 优惠金额验证
function compare(){
		var privilege,
		    totalInterest;
		privilege = $('#privilege').val();    
		totalInterest = $('#totalInterest').val();  
		if(Number(privilege) > Number(totalInterest)){
			alert("优惠金额太大");
			return false;
		}else{
			return true;
		}
	} 

// 还款交易
	function repay(){
		if(compare()){
		        // 优惠金额
		        var privilege,// 优惠金额
		            payType,// 还款类型
                    occDate, // 交易日期,默认为当前系统日期
                    bankNo; // 交易日期,默认为当前系统日期
		        
		            privilege = $('#privilege').val();
		            payType = $('#payType').val();
		            occDate = $('#occDate').val();
		            bankNo = $('#bankNo').val();
		            // 客户输入的金额
		            returnAmt = $('#returnAmt').val();
		            // 履约保证金额
		            perfAmount = $('#perfAmount').val();
		            if($('#isPerfAmount')==0){
		            	perfAmount = "0.00";
		            }
		            alert($('#repayForm').serialize());
			    $.ajax({
									     url:"repay_repayTrading.action?repayBean.privilege="+privilege+"&repayBean.payType="+payType+"&repayBean.occDate="+occDate+"&repayBean.bankNo="+bankNo+"&repayBean.returnAmt="+returnAmt+"&repayBean.perfAmount="+perfAmount,
									     dataType:'text',
									     data: $('#repayForm').serialize(),
									     type:"post",
									     dataType:"json",
									     success:function(data){
			    	alert("data="+data);
			    							if(data!=null){
			    								// 跳转到还款信息展示页面
			    								var dueNo = $("#repayBean\\.dueNo").val();
			    								alert("dueNo="+dueNo);
			    								alert("occDate="+occDate);
			    								window.location.href="repay_toRepayFish.action?repayBean.occDate="+occDate+"&repayBean.dueNo="+dueNo;
			    							}	
										 }
								    })
		}
	}
	
	
	
	