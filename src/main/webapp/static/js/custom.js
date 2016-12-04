function setPoint() {
    var id = $("#course_id").val();
    var form = $("#edit_point_model");
    var d = form.serializeArray();

    var points = [];
    for (var i = 0; i < d.length / 3;i++) {
        var p = {};
        if(d[i*3].value!="NULL")
            p.id=d[i*3].value;
        else
            p.id=null;
        p.name=d[i*3+1].value;
        p.percent=d[i*3+2].value;
        points.push(p);
    }

    $.ajax({
        url:"/course/edit/point/"+id,
        type:"post",
        data:JSON.stringify(points),
        dataType:"json",
        contentType:"application/json",
        success:function(data){
            window.location.href="/course/"+id;
        },error:function(data){
            window.location.href="/course/"+id;
        }
    });
}

function addPoint() {
    $("#point_list").append('<div class="form-group"><input type="hidden" name="id" class="form-control" value="NULL"><div class="col-sm-6"><input type="text" name="name" class="form-control" placeholder="请输入考核点"></div><div class="col-sm-4"><input type="text" name="percent" class="form-control" placeholder="请输入比重" value="0"></div><div class="col-sm-2"><div class="center"><button type="button" class="btn btn-danger" onclick="removePoint(this)"><span class="glyphicon glyphicon-remove"></span></button></div></div></div>');
}

function removePoint(b) {
    $(b).parent().parent().parent().remove();
    init();
}

function setMark() {
    var id = $("#course_id").val();
    var form = $("#edit_mark_model");
    var d = form.serializeArray();
    var point_num = $("#point_num").val();
    var student_num = $("#select_num").val();
    var marks = [];
    for(var i =0; i<student_num ; i++) {
        var student_id = d[i*(d.length/student_num)].value;
        for(var j=0; j<point_num;j++) {
            var m = {};
            m.user={id:student_id};
            m.point={id:d[i*(d.length/student_num)+1+j*2].value};
            m.mark=d[i*(d.length/student_num)+2+j*2].value;
            marks.push(m);
        }
    }

    $.ajax({
        url:"/course/mark/save",
        type:"post",
        data:JSON.stringify(marks),
        dataType:"json",
        contentType:"application/json",
        success:function(data){
            window.location.href="/course/"+id;
        },error:function(data){
            window.location.href="/course/"+id;
        }
    });
}

function replace() {
    var rs = $(".replace-show");
    var rh = $(".replace-hidden");

    rs.addClass("replace-hidden").removeClass("replace-show");
    rh.addClass("replace-show").removeClass("replace-hidden");
}

function init() {
    if($("#point_list").children().length == 1) {
        addPoint();
    }
}

$("#course_search").bind('keydown',function (event) {
    var v = $("#course_search").val().trim();
    if(event.keyCode == "13" && v.length>0) {
        window.location.href="/course/search/"+v
    }
});

$("#user_search").bind('keydown',function (event) {
    var v = $("#user_search").val().trim();
    if(event.keyCode == "13" && v.length>0) {
        window.location.href="/user/search/"+v
    }
});