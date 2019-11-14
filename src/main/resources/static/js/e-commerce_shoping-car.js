$(".search-product").on("click", function(){
    $(".search-product").addClass("font-aqua");
    $(".search-service").removeClass("font-aqua");
})
$(".search-service").on("click", function(){
    $(".search-service").addClass("font-aqua");
    $(".search-product").removeClass("font-aqua");
})

$(".banner a").on("click", function(event){
    $(".banner a").removeClass("border-b");
    $(event.target).addClass("border-b");
})

$(".content-nav li").on("click", function(event){
    $(".content-nav a").removeClass("nav-active");
    $(event.target).addClass("nav-active");
})

$(".search-btn").on("click", function(event){
    var id=$(".search input").val();
    location.href="e_shoppingcar?id="+id;
})
$(document).ready(function(){
		var pcount=$('#count').val();
		var psize=$('#pageSize').val();
		var pstart=$('#pageStart').val();
		var id=$('#id').val();
		var nowpage=Math.floor(pstart/psize)+1;		
		var cpage=Math.ceil(pcount/psize);
		var strhtml="";
		
		if(cpage<=10){
			for(var i=1;i<=cpage;i++){
				if(i==nowpage){
					strhtml+='<a href=/e_shoppingcar?pageStart='+psize*(i-1)+'&id='+id+' style="background-color:#aaaaaa;font-weight:700">'+i+'</a>';
				}else{
					strhtml+='<span> <a href=/e_shoppingcar?pageStart='+psize*(i-1)+'&id='+id+'>'+i+'</a></span>';
				}
			}
		}
		else if(cpage>10){
		
			if( 1<=nowpage && nowpage<=6){		
			
				for(var i=1;i<=10;i++){			
				
					if(i==nowpage){
						strhtml+='<a href=/e_shoppingcar?pageStart='+psize*(i-1)+'&id='+id+' style="background-color:#aaaaaa;font-weight:700">'+i+'</a>';
					}else{
						strhtml+='<span> <a href=/e_shoppingcar?pageStart='+psize*(i-1)+'&id='+id+'>'+i+'</a></span>';
					}
				}
			}
			else if( nowpage<=cpage-4){
			
				for(var i=nowpage-5;i<=nowpage+4;i++){			
			
					if(i==nowpage){
						strhtml+='<a href=/e_shoppingcar?pageStart='+psize*(i-1)+'&id='+id+' style="background-color:#aaaaaa;font-weight:700">'+i+'</a>';
					}else{
						strhtml+='<span> <a href=/e_shoppingcar?pageStart='+psize*(i-1)+'&id='+id+'>'+i+'</a></span>';
					}
				
				}
			} 
			else if( cpage-4<nowpage && nowpage<=cpage){
			
				for(var i=cpage-9;i<=cpage;i++){
				
					if(i==nowpage){
						strhtml+='<a href=/e_shoppingcar?pageStart='+psize*(i-1)+'&id='+id+' style="background-color:#aaaaaa;font-weight:700">'+i+'</a>';
					}else{
						strhtml+='<span> <a href=/e_shoppingcar?pageStart='+psize*(i-1)+'&id='+id+'>'+i+'</a></span>';
					}
				}
			}
			else{
				console.error(00000000);
			}
		}
		else{  console.error(00000000); }
		$("#mydiv").html(strhtml);

			});


function deletes(id){
	console.log("----",id);
	$.ajax({
		//请求类型
		type:"post",
		//请求路径
		url:"/e_delete",
		//请求参数
		data:{
			id:id,
		},
		//返回数据类型
		//请求成功后调用函数
		success: function(data){
			console.log("成功后返回数据",data);
			if(data.code == "success"){
				location.href = "/e_shoppingcar"
			}
			else if(data.code == "false"){
				alert("删除失败!");	
			}
			
		},
		error: function(data){
			console.log("失败后返回数据",data);
		}
	})
}





$(function() {
	//对商品进行累加操作
		$(".add").click(function() {
			var t = $(this).parent().find('input[class*=text_box]');
			if(t.val()==""||undefined||null){
				t.val(0);
			}
			t.val(parseInt(t.val()) + 1)
			setTotal();
		})
		//对商品进行累减操作
		$(".min").click(function() {
			var t = $(this).parent().find('input[class*=text_box]');
			if(t.val()==""||undefined||null){
				t.val(0);
			}
			t.val(parseInt(t.val()) - 1)
			if(parseInt(t.val()) < 0) {
				t.val(0);
			}
			setTotal();
		})
		$(".text_box").keyup(function(){
			var t = $(this).parent().find('input[class*=text_box]');
			if(parseInt(t.val())==""||undefined||null || isNaN(t.val())) {
				t.val(0);
			}
			setTotal();
		})
		//计算总价
		function setTotal() {
			var s = 0;
			//进行遍历商品的td
			$("#tab td").each(function() {
			        //获取商品的单价
			        var p = $(this).find('span[class*=price]').text();
			        //获取商品的数量
				var t = $(this).find('input[class*=text_box]').val();
				if(parseInt(t)==""||undefined||null || isNaN(t) || isNaN(parseInt(t))){
					t=0;
				}
				//计算总价
				s += parseInt(t) * parseFloat(p);
			});
			$("#total").html(s.toFixed(2));
		}
		setTotal();
	})
