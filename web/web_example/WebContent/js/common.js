function ready(func) {
  if (document.body == null) {
	setTimeout(function() { ready(func); }, 100);
  } else {
	func();
  }
}

// 달력 셋팅
function showCalendar(o, y, m, d, nxt) { 
  var text = '<table>\n<tr><td colspan=7 style="text-align:center">'; 
  if (typeof nxt == 'undefined') {
    text += '<span onclick="showCalendar('+(y-1)+','+m+','+d+')"> Y- </span>'; 
    text += '<span onclick="showCalendar('+(m==1?(y-1)+','+12:y+','+(m-1))+','+d+')"> M- </span>'; 
  }
  text += '[' + y + '/' + ((m < 10) ? ('0' + m) : m) + ']'; 
  if (typeof nxt == 'undefined') {
    text += '<span onclick="showCalendar('+(m==12?(y+1)+','+1:y+','+(m+1))+','+d+')"> M+ </span>'; 
    text += '<span onclick="showCalendar('+(y+1)+','+m+','+d+')"> Y+ </span>'; 
  }
  text += '</td>'; 

  var d1 = (y+(y-y%4) / 4-(y-y%100) / 100+(y-y%400) / 400 + m*2+(m*5-m*5%9) / 9-(m<3?y%4||y%100==0&&y%400?2:3:4)) % 7; 
  for (i = 0; i < 42; i++) { 
    if (i%7==0) text += '</tr>\n<tr>'; 
    if (i < d1 || i >= d1+(m*9-m*9%8)/8%2+(m==2?y%4||y%100==0&&y%400?28:29:30)) 
      text += '<td> </td>'; 
    else {
      text += '<td style="' + (i%7 ? '' : 'color:red;') + '">';
      if (typeof nxt == 'undefined') text += '<a href="?year=' + y + '&month=' + m + '&day=' + (i+1-d1) + '" style="' + (i%7 ? '' : 'color:red;') + '">';
      if (i+1-d1 == d) text += '<strong style="background:#000; color:#fff;">';
      text += (i+1-d1);
      if (i+1-d1 == d) text += '</strong>';
      if (typeof nxt == 'undefined') text += '</a>';
      text += '</td>';
    }
  } 
  document.getElementById(o).innerHTML = text + '</tr>\n</table>'; 
}

// 상품 구매시 미 등록 상품 필드 체크 관련
function checkBuyProduct(form) {
  var nameObj = $(form).find('input.name');
  var stockObj = $(form).find('input.stock');
  for (var i = 0; i < nameObj.length; i++) {
	// 사용자가 미등록 물품명을 입력시 상품명에 바코드 번호를 잘못 입력하는 절차를
	// 방지하기 위한 루틴 추가
	// ---------------------------------------
	// 2015-04-28 modify by 24-1 김병욱
	// ($ + 1) ~ ($ + 6)
	if (/^[\s\t]*[0-9]+[\s\t]*$/.test($(nameObj[i]).val())) {
	  alert('바코드가 아니라 상품명을 입력하여 주시기 바랍니다.');
      nameObj[i].focus();
      return false;
	}
    if ($(nameObj[i]).val() == '') {
      alert('미 등록된 상품의 상품명은 공란이 될 수 없습니다.');
      nameObj[i].focus();
      return false;
    }
  }
  for (var i = 0; i < stockObj.length; i++) {
	// 구입 개수란에 바코드가 찍히는 상황을 방지하는 루틴 추가
	// 단일 상품의 최대 구입개수를 1000개로 제한
	// ----------------------------------------
	// 2015-05-27 modify by 24-1 김병욱
	if ($(stockObj[i]).val() > 1000) {
	  alert('바코드가 아니라 상품명을 입력하여 주시기 바랍니다.');
	  $(stockObj[i]).val(1);
      stockObj[i].focus();
      return false;
	}
  }
  return true;
}

// 삼품 구매시 관련 상품명 나오도록 AJAX
// start {{
var searchIndex = 0;
function closeSearch() {
  var searchObj = $('#search');
  searchObj.html('');
  searchObj.css({ 'border' : '0px', 'background' : 'transparent' });
  searchIndex = 0;
}
function openSearch(input) {
  var searchObj = $('#search');
  //console.log(event.keyCode);
  switch (event.keyCode) {
    case 38: searchIndex--; break; // up
    case 40: searchIndex++; break; // down
    case 13: // enter
      if (searchObj.find('a').length <= 0) return false;
      closeSearch();
      break;
    case 27:
		// 사용자 편의성을 위한 UX 적인 부분 수정
		// esc 누를 경우 자동완성 기능 종료
		// ------------------------------------------
		// 2015-04-28 modify by 24-1 김병욱
		//$(input).val('');
		$(input).blur();
		closeSearch();
		return false;
		break; // esc
  }
  if (event.keyCode != 13) {
    // 상품명으로 검색
    $.ajax({
      type : 'GET',
      url : './search.product.ajax.php',
      data : 'productName=' + encodeURI($(input).val()),
      dataType : 'json',
      success : function (data) {
        var link = '';
        if (data.length > 0) {
          for (var i = 0; i < data.length; i++) {
            link += '<a href="#" onclick="searchIndex = ' + i + ';';
			link += 'document.getElementById(\'buyform\').productBarcode.value=\'' + data[i].productName + '\';';
			link += 'openSearch(document.getElementById(\'buyform\').productBarcode);';
			link += '$(\'#buyform\').submit();';
			link += 'closeSearch();">' + data[i].productName + '</a>';
            link += '<br />';
          }
        } else {
          link += '검색된 상품이 없습니다.(등록 필요)';
        }
        searchObj.html(link);
        searchObj.css({ 'border' : '1px solid #999', 'background' : '#fff' });

        if (searchIndex < 0) searchIndex = 0;
        if (searchIndex > data.length - 1) searchIndex = data.length - 1;

        var thisIndexObj = searchObj.find('a');
        //console.log(thisIndexObj);
        for (var i = 0; i < thisIndexObj.length; i++) {
          $(thisIndexObj[i]).css({ 'background' : '#fff', 'color' : '#000' });
        }
        $(thisIndexObj[searchIndex]).css({ 'background' : '#000', 'color' : '#fff' });
      }
    });
  }
}
// }} end

// 상품 구매 관련
function appendProduct(form) {
  var productBarcode = form.productBarcode.value;

  var success = function (data) {
    console.log(data);
    if (typeof data.length != 'undefined') data = data[searchIndex];
    if ($('#info' + data.productID + '_' + data.productBarcode).length <= 0) {
      var source = '';
      source += '<input type="hidden" name="productID[]" value="' + data.productID + '" />';
      source += '<input type="hidden" name="productPrice[]" value="' + data.productPrice + '" />';
      source += '<input type="hidden" name="productBarcode[]" value="' + data.productBarcode + '" />';
      source += '<ul id="info' + data.productID + '_' + data.productBarcode + '" class="table table-1' + ($('#buylist ul').length % 2) + '">';
      if (typeof data.productRegister != 'undefined') {
        source += '<li><span><input type="text" class="form-input-text w100 name" name="productName[]" value="' + data.productName + '" placeholder="Name" /></span></li>';
      } else {
        source += '<li><span>' + data.productName + '</span></li>';
        source += '<input type="hidden" name="productName[]" value="' + data.productName + '" />';
      }
      source += '<li><span>' + data.productBarcode + '</span></li>';
      if (data.productPriceModifyDate == "0000-00-00 00:00:00") {
        source += '<li><span><strong>미 등록 상품</strong></span></li>';
      } else {
        source += '<li><span><strong>' + data.productPrice + '</strong> 원</span></li>';
      }
      source += '<li><span><input type="number" min="1" class="form-input-text w100 stock" name="productStock[]" value="1" placeholder="Stock" /></span></li>';
      source += '</ul>';
      $('#buylist').append(source);
    } else {
      var stockObj = $('#info' + data.productID + '_' + data.productBarcode).find('input.stock');
      stockObj.val(Number(stockObj.val()) + 1);
    }
    form.productBarcode.value = '';
  };

  /*
  if (productName != '') {
    // 상품명으로 검색
    $.ajax({
      type : 'GET',
      url : './search.product.php',
      data : 'productName=' + productName,
      dataType : 'json',
      success : success
    });
  } else
  */
  if (productBarcode != '' && /^[0-9]+$/.test(productBarcode) == true) {
    // 바코드로 검색
    $.ajax({
      type : 'GET',
      url : './search.product.php',
      data : 'productBarcode=' + productBarcode,
      dataType : 'json',
      success : success
    });
  } else if (productBarcode != '' && /^[0-9]+$/.test(productBarcode) == false) {
    // 이름 검색
    $.ajax({
      type : 'GET',
      url : './search.product.ajax.php',
      data : 'productName=' + encodeURI(productBarcode),
      dataType : 'json',
      success : success
    });
  }
  return false;
}

ready(function() {
  // sidebar 시간 셋팅
  var timeObj = document.getElementsByClassName('time');
  var timeSettings = function() {
    var datetime = new Date();

    timeObj[0].className = timeObj[0].className.replace(/time\-[0-9]+/, 'time-' + Math.floor(datetime.getHours() / 10));
    timeObj[1].className = timeObj[1].className.replace(/time\-[0-9]+/, 'time-' + datetime.getHours() % 10);

    timeObj[3].className = timeObj[3].className.replace(/time\-[0-9]+/, 'time-' + Math.floor(datetime.getMinutes() / 10));
    timeObj[4].className = timeObj[4].className.replace(/time\-[0-9]+/, 'time-' + datetime.getMinutes() % 10);

    setTimeout(timeSettings, 5000);
  };
  timeSettings();

  // 버튼 링크 셋팅
  $('button.btn').click(function() {
    if (typeof $(this).attr('data-href') != 'undefined') {
      location.href = $(this).attr('data-href');
    }
  });

  // sideMenu 관련
  var sideMenu = $('.side-menu li');
  for (var i = 0; i < sideMenu.length; i++) {
    $(sideMenu[i]).click(function() {
      var index = $(this).index();
      var indexObj = $('.side-menu li ul');

      for (var i = 0; i < indexObj.length; i++) {
        if (index != i) {
          $($('.side-menu li ul')[i]).css('display', 'none');
        } else {
          $($('.side-menu li ul')[i]).toggle();
        }
      }
    });
    $(sideMenu[i]).mouseover(function() {
      $(this).addClass('active');
   });
    $(sideMenu[i]).mouseout(function() {
      if ($(this).attr('class').replace('select', '') == $(this).attr('class'))
        $(this).removeClass('active');
    });
  }
});
