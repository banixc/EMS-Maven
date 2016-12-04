<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="base/header.jsp" %>

<h1 class="page-header">${course.name}</h1>

<c:if test="${current.admin}">
    <div class="tools-button">
        <div class="btn-group">
            <button type="button" class="btn btn-info" data-toggle="modal" data-target="#edit_message_model">修改课程信息
            </button>
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#edit_point_model"
                    onclick="init(this)">设置考核点
            </button>
            <c:if test="${course.points != null and !empty course.points and !empty course.selectors}">
                <button type="button" class="btn btn-success" data-toggle="modal" data-target="#edit_mark_model">录入分数
                </button>
            </c:if>
        </div>
    </div>
</c:if>

<h3>课程信息</h3>
<hr/>

<div class="form-horizontal">
    <div class="form-group">
        <label class="col-sm-2 control-label">讲师</label>
        <div class="col-sm-10">
            <p class="form-control-static col-md-8">${course.teacherName}</p>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">学分</label>
        <div class="col-sm-10">
            <p class="form-control-static col-md-8">${course.credit}</p>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">限选人数</label>
        <div class="col-sm-10">
            <p class="form-control-static col-md-8">${course.num}</p>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">已选人数</label>
        <div class="col-sm-10">
            <input hidden="hidden" id="select_num" value="${course.selectors.size()}">
            <p class="form-control-static col-md-8">${course.selectors.size()}</p>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">开课学年</label>
        <div class="col-sm-10">
            <p class="form-control-static col-md-8">${course.schoolYear.name}</p>
        </div>
    </div>
</div>

<c:if test="${course.points != null and !empty course.points}">
    <h3>考核点(${course.points.size()})</h3>
    <hr/>
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>考核点名称</th>
            <th>比重</th>
            <c:if test="${course.canCancel(current)}">
                <th>我的分数</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <input hidden="hidden" id="point_num" value="${course.points.size()}">
        <c:forEach var="point" items="${course.points}">
            <tr>
                <td>${point.name}</td>
                <td>${point.percent}%</td>
                <c:if test="${course.canCancel(current)}">
                    <td>${point.getMarkByStudent(current)}</td>
                </c:if>
            </tr>
        </c:forEach>
        <c:if test="${course.canCancel(current)}">
            <c:set var="markSum" value="${current.getSumMarkByCourse(course)}"/>
            <tr>
                <td>总成绩</td>
                <td>100%</td>
                <td>${markSum.mark}</td>
            </tr>
            <tr>
                <td>实得学分</td>
                <td>&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;</td>
                <td>${markSum.credit}</td>
            </tr>
        </c:if>
    </tbody>
    </table>
</c:if>

<c:if test="${current.admin}">
    <h3>已选课学生(${course.selectors.size()})</h3>
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>学生姓名</th>
            <c:forEach var="point" items="${course.points}">
                <th>${point.name}</th>
            </c:forEach>
            <th>总分</th>
            <th>学分</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="student" items="${course.selectors}">
            <tr>
                <td>${student.name}</td>
                <c:forEach var="mark_point" items="${course.points}">
                    <td>
                        <c:set var="mark" value="${student.getMarkByPoint(mark_point)}"/>
                        <c:if test="${mark != null}">${mark.mark}</c:if><c:if test="${mark == null}">0</c:if>
                    </td>
                </c:forEach>
                <c:set var="markSum" value="${student.getSumMarkByCourse(course)}"/>
                <td>${markSum.mark}</td>
                <td>${markSum.credit}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<!-- edit_message_model -->
<form class="modal fade form-horizontal" id="edit_message_model" tabindex="-1" role="dialog"
      aria-labelledby="myModalLabel" aria-hidden="true" action="/course/edit/message" method="post">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">修改课程信息</h4>
            </div>
            <div class="modal-body">
                <input type="hidden" id="course_id" name="id" class="form-control" value="${course.id}">
                <div class="form-group">
                    <label for="input_course_name" class="col-sm-2 control-label">课程名称</label>
                    <div class="col-sm-10">
                        <input type="text" name="name" class="form-control" id="input_course_name" placeholder="请输入课程名称"
                               value="${course.name}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="input_course_num" class="col-sm-2 control-label">限选人数</label>
                    <div class="col-sm-10">
                        <input type="text" name="num" class="form-control" id="input_course_num" placeholder="请输入限选人数"
                               value="${course.num}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="input_course_teacher" class="col-sm-2 control-label">讲师</label>
                    <div class="col-sm-10">
                        <input type="text" name="teacherName" class="form-control" id="input_course_teacher"
                               placeholder="请输入讲师姓名" value="${course.teacherName}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="input_course_credit" class="col-sm-2 control-label">学分</label>
                    <div class="col-sm-10">
                        <input type="text" name="credit" class="form-control" id="input_course_credit"
                               placeholder="请输入学分" value="${course.credit}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="input_course_credit" class="col-sm-2 control-label">开课学年</label>
                    <div class="col-sm-10">
                        <select class="form-control" name="schoolYear.id">
                            <c:forEach var="schoolYear" items="${schoolYears}">
                                <option value="${schoolYear.id}"
                                        <c:if test="${course.schoolYear.id == schoolYear.id}">selected="selected"</c:if>>${schoolYear.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="submit" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</form>

<!-- edit_point_model -->
<form class="modal fade form-horizontal bs-example-modal-sm" id="edit_point_model" tabindex="-1" role="dialog"
      aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">设置考核点</h4>
            </div>
            <div class="modal-body" id="point_list">
                <div class="form-group">
                    <div class="col-sm-6">
                        <div class="center">
                            考核点
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="center">
                            比重
                        </div>
                    </div>
                    <div class="col-sm-2">
                        <div class="center">
                            操作
                        </div>
                    </div>
                </div>
                <c:forEach var="point" items="${course.points}">
                    <div class="form-group">
                        <input type="hidden" name="id" class="form-control" value="${point.id}">
                        <div class="col-sm-6">
                            <input type="text" name="name" class="form-control" placeholder="请输入考核点"
                                   value="${point.name}">
                        </div>
                        <div class="col-sm-4">
                            <input type="text" name="percent" class="form-control" placeholder="请输入比重"
                                   value="${point.percent}">
                        </div>
                        <div class="col-sm-2">
                            <div class="center">
                                <button type="button" class="btn btn-danger" onclick="removePoint(this)"><span
                                        class="glyphicon glyphicon-remove"></span></button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-warning" id="edit_point_model_add" onclick="addPoint()">添加</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="setPoint()" id="edit_point_model_save">保存
                </button>
            </div>
        </div>
    </div>
</form>

<!-- edit_mark_model -->
<form class="modal fade form-horizontal bs-example-modal-lg" id="edit_mark_model" tabindex="-1" role="dialog"
      aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">录入分数</h4>
            </div>
            <div class="modal-body">
                <table class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th>学生姓名</th>
                        <c:forEach var="point" items="${course.points}">
                            <th>${point.name}</th>
                        </c:forEach>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="student" items="${course.selectors}">
                        <tr>
                            <td><p class="form-control-static">${student.name}</p><input hidden="hidden"
                                                                                         name="student_id"
                                                                                         value="${student.id}"></td>
                            <c:forEach var="mark_point" items="${course.points}">
                                <td>
                                    <input hidden="hidden" name="point_id" value="${mark_point.id}">
                                    <c:set var="mark" value="${student.getMarkByPoint(mark_point)}"/>
                                    <input type="text" name="mark" class="form-control" placeholder="请输入分数"
                                           value="<c:if test="${mark != null}">${mark.mark}</c:if><c:if test="${mark == null}">0</c:if>">
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="setMark()">保存</button>
            </div>
        </div>
    </div>
</form>

<%@include file="base/footer.jsp" %>