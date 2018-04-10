/**
 * @author Roger Wu
 */

(function($){
	var allSelectBox = [];
	var killAllBox = function(bid){
		$.each(allSelectBox, function(i){
			if (allSelectBox[i] != bid) {
				if (!$("#" + allSelectBox[i])[0]) {
					$("#op_" + allSelectBox[i]).remove();
					//allSelectBox.splice(i,1);
				} else {
					$("#op_" + allSelectBox[i]).css({ height: "", width: "" }).hide();
				}
				$(document).unbind("click", killAllBox);
			}
		});
	};
	
	var _onchange = function(event){
		var $ref = $("#"+event.data.ref);
		var $parent = event.data.$this;

		if ($ref.size() == 0) return false;

		if (encodeURIComponent(event.data.$this.val()) == "" &&
			$parent.attr("callIfEmpty") == "false") {
            var html = '<option value="">请选择</option>';
            var $refCombox = $ref.parents("div.combox:first");
            $ref.html(html).insertAfter($refCombox);
            $refCombox.remove();
            $ref.trigger("change").combox();
            return;
		}

		$.ajax({
			type:'GET', dataType:"json", url:event.data.refUrl.replace("{value}", encodeURIComponent(event.data.$this.val())), cache: false,
			data:{},
			success: function(json){
				_comboxRefresh($ref, event.data.$this, event.data.$this.attr("data") ? json[event.data.$this.attr("data")] : json);
			},
			error: DWZ.ajaxError
		});
	};

	var _comboxRefresh = function($select, $parent, json){
		if (!json) return;
		var html = '';

		if (!json.length && $parent.attr("group")) {
            json = json[$parent.attr("group")];
		}

		$.each(json, function(i){
			if ($parent.attr("optName") && $parent.attr("optVal")) {
				html += '<option value="' + json[i][$parent.attr("optVal")] + '">' + json[i][$parent.attr("optName")] + '</option>';
			} else {
                if (json[i] && json[i].length > 1) {
                    html += '<option value="' + json[i][0] + '">' + json[i][1] + '</option>';
                }
			}
		});

		var $refCombox = $select.parents("div.combox:first");
		$select.html(html).insertAfter($refCombox);
		$refCombox.remove();
		$select.trigger("change").combox();
	};

	var _comboxReset = function($select){
		var $box = $select.parents('div.select:first'),
			defaultValue = $box.find('>a').attr('default-value');
		$('#op_'+$box.attr('id')).find('>li a[value="'+defaultValue+'"]').trigger('click');
	};
					
	$.extend($.fn, {
		comboxSelect: function(options){
			var op = $.extend({ selector: ">a" }, options);
			
			return this.each(function(){
				var box = $(this);
				var selector = $(op.selector, box);

				allSelectBox.push(box.attr("id"));
				$(op.selector, box).click(function(){
					if (! box.hasClass('disabled')) {
						var options = $("#op_"+box.attr("id"));
						if (options.is(":hidden")) {
							if(options.height() > 300) {
								options.css({height:"300px",overflow:"scroll"});
							}
							var top = box.offset().top+box[0].offsetHeight-50;
							if(top + options.height() > $(window).height() - 20) {
								top =  $(window).height() - 20 - options.height();
							}
							var boxWidth = box.css("width");
							if (boxWidth) {
								options.css({width: parseInt(boxWidth) - 20 });
							}

							options.css({top:top,left:box.offset().left}).show();
							killAllBox(box.attr("id"));
							$(document).click(killAllBox);
						} else {
							$(document).unbind("click", killAllBox);
							killAllBox();
						}
					}

					return false;
				});
				$("#op_"+box.attr("id")).find(">li").comboxOption(selector, box);		
			});
		},
		comboxOption: function(selector, box){
			return this.each(function(){
				$(">a", this).click(function(){
					var $this = $(this);
					$this.parent().parent().find(".selected").removeClass("selected");
					$this.addClass("selected");
					selector.text($this.text());
					
					var $input = $("select", box);
					if ($input.val() != $this.attr("value")) {
						$("select", box).val($this.attr("value")).trigger("change");
					}
				});
			});
		},
		combox:function(){
			/* 清理下拉层 */
			var _selectBox = [];
			$.each(allSelectBox, function(i){ 
				if ($("#" + allSelectBox[i])[0]) {
					_selectBox.push(allSelectBox[i]);
				} else {
					$("#op_" + allSelectBox[i]).remove();
				}
			});
			allSelectBox = _selectBox;
			
			return this.each(function(i){
				var $this = $(this).hide();

				var name = $this.attr("name");
				var value= $this.val();
				var label = $('option[value="' + value + '"]',$this).text();
				var ref = $this.attr("ref");
				var refUrl = $this.attr('refUrl') || '';

				if (refUrl && openApiContextPath) {
					refUrl = openApiContextPath + "/" + refUrl;
				}

				var resetValue = $this.attr('reset-value') !== undefined ? $this.attr('reset-value') : value

				var width = $this.css("width");

				var cid = $this.attr("id") || Math.round(Math.random()*10000000);
				var select = '<div class="combox" ' + (width ? 'style="width:' + width + '"' : '') + '><div id="combox_'+ cid +'" class="select"' + (ref?' ref="' + ref + '"' : '') + '>';
				select += '<a '+ (width ? 'style="width:' + (parseInt(width) - 28) + 'px"' : '') +  + ' href="javascript:" name="' + name +'" value="' + value + '" default-value="'+resetValue+'">' + label +'</a></div></div>';
				var options = '<ul ' + (width ? 'style="width:' + (parseInt(width) - 28) + 'px"' : '') + 'class="comboxop" id="op_combox_'+ cid +'">';
				$("option", $this).each(function(){
					var option = $(this);
					options +="<li><a " + (width ? "style='display:block; width:" + (parseInt(width) - 30) + "px'" : '') + " class=\""+ ((value||option[0].text) && value==option[0].value?"selected":"") +"\" href=\"#\" value=\"" + option[0].value + "\">" + option[0].text + "</a></li>";
				});
				options +="</ul>";
				
				$("body").append(options);
				$this.after(select);
				$("div.select", $this.next()).comboxSelect().append($this);
				
				if (ref && refUrl) {
					$this.unbind("change", _onchange).bind("change", {ref:ref, refUrl:refUrl, $this:$this}, _onchange);
				}
			});
		},

		// combox 刷新全部 option 项
		comboxRefresh: function(json){
			return this.each(function(){
				var $select = $(this);
				_comboxRefresh($select, json);
			});
		},

		// combox reset还原初始值
		comboxReset: function(){
			return this.each(function(){
				var $select = $(this);
				_comboxReset($select);
			});
		},

		comboxDisable: function(){
			return this.each(function(){
				$(this).parents('.combox .select:first').addClass('disabled');
			});
		},

		comboxEnable: function(){
			return this.each(function(){
				$(this).parents('.combox .select:first').removeClass('disabled');
			});
		}

	});
})(jQuery);
