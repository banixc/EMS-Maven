<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="base/header.jsp" %>

<h1 class="page-header"><c:if test="${is_select_page != null && is_select_page}">已选</c:if>课程列表
    <c:if test="${schoolYear != null}">
        <small>${schoolYear.name}(${schoolYear.start} - ${schoolYear.end})</small>
    </c:if>
</h1>

<c:if test="${is_select_page == null || !is_select_page}">
    <div class="tools-button">
        <c:if test="${current.admin}">
            <div class="btn-group">
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#add_course_modal">添加课程</button>
                <button type="button" class="btn btn-info" data-toggle="modal" data-target="#add_shcool_year_modal">添加学年</button>
            </div>
        </c:if>

        <div class="btn-group">
            <div class="dropdown in-line">
                <button class="btn btn-success dropdown-toggle" type="button" data-toggle="dropdown">
                    <c:if test="${schoolYear != null}">
                        ${schoolYear.name}
                    </c:if>
                    <c:if test="${schoolYear == null}">
                        所有学年
                    </c:if>
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                    <li><a href="/course/list/1">所有学年</a></li>
                    <c:forEach items="${schoolYears}" var="sy">
                        <li><a href="/course/year/${sy.id}/list/1">${sy.name}</a></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <c:if test="${schoolYear != null}">
            <div class="btn-group">
                <button type="button" class="btn btn-success" data-toggle="modal" data-target="#edit_school_year_modal">
                    编辑学年
                </button>
                <button type="button" class="btn btn-danger" onclick="location='/course/year/remove/${schoolYear.id}'">
                    删除学年
                </button>
            </div>
        </c:if>

        <div class="btn-group">
            <div class="form-inline" role="form">
                <div class="form-group">
                    <label class="sr-only" for="course_search">Search</label>
                    <input type="text" class="form-control" id="course_search" placeholder="搜索课程或讲师"
                    <c:if test="${search != null}"> value="${search}"</c:if>>
                    <span class="glyphicon glyphicon-search form-control-feedback"></span>
                </div>
            </div>
        </div>
    </div>
</c:if>
<table class="table table-hover table-bordered">
    <thead>
    <tr>
        <th>名称</th>
        <th>选课状态</th>
        <th>讲师</th>
        <th>学年</th>
        <c:if test="${!current.admin}">
            <th>操作</th>
        </c:if>
        <c:if test="${is_select_page != null && is_select_page}">
            <th>总分</th>
            <th>学分</th>
        </c:if>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="course" items="${pagination.list}">
        <tr>
            <td><a class="unlink" href="/course/${course.id}">${course.name}</a></td>
            <td>${course.selectors.size()} / ${course.num}</td>
            <td>${course.teacherName}</td>
            <td>${course.schoolYear.name}</td>
            <c:if test="${!current.admin}">
                <td>
                    <c:if test="${course.canSelect(current)}">
                        <button type="button" class="btn btn-success btn-xs"><span class="glyphicon glyphicon-ok"
                                                                                   onclick="location='/course/select/${course.id}'"></span>
                        </button>
                    </c:if>
                    <c:if test="${course.canCancel(current)}">
                        <button type="button" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"
                                                                                  onclick="location='/course/cancel/${course.id}'"></span>
                        </button>
                    </c:if>
                </td>
            </c:if>
            <c:if test="${is_select_page != null && is_select_page}">
                <td>${current.getSumMarkByCourse(course).mark}</td>
                <td>${current.getSumMarkByCourse(course).credit} / ${course.credit}</td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>

<u:pagination pagination="${pagination}"/>

<!-- add_course_modal -->
<form class="modal fade form-horizontal" id="add_course_modal" tabindex="-1" role="dialog"
      aria-labelledby="myModalLabel" aria-hidden="true" action="/course/add" method="post">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">添加课程</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="input_course_name" class="col-sm-2 control-label">课程名称</label>
                    <div class="col-sm-10">
                        <input type="text" name="name" class="form-control" id="input_course_name"
                               placeholder="请输入课程名称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="input_course_num" class="col-sm-2 control-label">限选人数</label>
                    <div class="col-sm-10">
                        <input type="text" name="num" class="form-control" id="input_course_num" placeholder="请输入限选人数">
                    </div>
                </div>
                <div class="form-group">
                    <label for="input_course_teacher" class="col-sm-2 control-label">讲师</label>
                    <div class="col-sm-10">
                        <input type="text" name="teacherName" class="form-control" id="input_course_teacher"
                               placeholder="请输入讲师姓名">
                    </div>
                </div>
                <div class="form-group">
                    <label for="input_course_credit" class="col-sm-2 control-label">学分</label>
                    <div class="col-sm-10">
                        <input type="text" name="credit" class="form-control" id="input_course_credit"
                               placeholder="请输入学分">
                    </div>
                </div>

                <div class="form-group">
                    <label for="input_course_credit" class="col-sm-2 control-label">开课学年</label>
                    <div class="col-sm-10">
                        <select class="form-control" name="schoolYear.id">
                            <c:forEach var="sy" items="${schoolYears}">
                                <option value="${sy.id}"
                                        <c:if test="${sy.id == schoolYear.id}">selected="selected"</c:if>>${sy.name}</option>
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

<!-- add_school_year_modal -->
<form class="modal fade form-horizontal" id="add_shcool_year_modal" tabindex="-1" role="dialog"
      aria-labelledby="myModalLabel" aria-hidden="true" action="/course/year/add" method="post">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">添加学年</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="col-sm-2 control-label">学年名称</label>
                    <div class="col-sm-10">
                        <input type="text" name="name" class="form-control" placeholder="请输入学年名称">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">开始时间</label>
                    <div class="col-sm-10">
                        <input type="date" name="start" class="form-control" placeholder="请输入开始日期">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">结束时间</label>
                    <div class="col-sm-10">
                        <input type="date" name="end" class="form-control" placeholder="请输入结束日期">
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

<c:if test="${schoolYear != null}">
    <!-- edit_school_year_modal -->
    <form class="modal fade form-horizontal" id="edit_school_year_modal" tabindex="-1" role="dialog"
          aria-labelledby="myModalLabel" aria-hidden="true" action="/course/year/edit" method="post">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span
                            class="sr-only">Close</span></button>
                    <h4 class="modal-title">修改学年</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">学年名称</label>
                        <div class="col-sm-10">
                            <input type="text" name="name" class="form-control" placeholder="请输入学年名称"
                                   value="${schoolYear.name}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">开始时间</label>
                        <div class="col-sm-10">
                            <input type="date" name="start" class="form-control" placeholder="请输入开始日期"
                                   value="${schoolYear.start}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">结束时间</label>
                        <div class="col-sm-10">
                            <input type="date" name="end" class="form-control" placeholder="请输入结束日期"
                                   value="${schoolYear.end}">
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
</c:if>

<%@include file="base/footer.jsp" %>