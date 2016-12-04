<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="base/header.jsp" %>

<h1 class="page-header">${user.name}</h1>

<form class="form-horizontal" role="form" action="/user/edit" method="post">

    <c:if test="${current.admin || current.id == user.id}">
    <div class="tools-button">
        <div class="btn-toolbar" role="toolbar">
            <div class="btn-group">
                <button type="button" class="btn btn-info" onclick="replace()">修改</button>
                <c:if test="${current.admin}"><button type="button" class="btn btn-danger" onclick="location='/user/remove/${user.id}'">删除</button></c:if>
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#reset_password_modal">重设密码</button>
            </div>
            <div class="btn-group">
                <button type="submit" class="btn btn-success replace-hidden">保存</button>
                <button type="button" class="btn btn-warning replace-hidden" onclick="replace()">取消</button>
            </div>
        </div>
    </div>
    </c:if>

    <div class="well well-lg">

        <div class="form-group">
            <label class="col-sm-2 control-label">ID</label>
            <div class="col-sm-5">
                <input type="hidden" class="form-control" name="id" value="${user.id}">
                <p class="form-control-static col-md-8">${user.id}</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">学号/工号</label>
            <div class="col-sm-5">
                <input type="hidden" class="form-control" name="uid" value="${user.uid}">
                <p class="form-control-static col-md-8">${user.uid}</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">姓名</label>
            <div class="col-sm-5">
                <input type="text" class="form-control replace-hidden" name="name" value="${user.name}">
                <p class="form-control-static col-md-8 replace-show">${user.name}</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">手机</label>
            <div class="col-sm-5">
                <input type="text" class="form-control replace-hidden" name="phone" value="${user.phone}">
                <p class="form-control-static col-md-8 replace-show">${user.phone}</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">Email</label>
            <div class="col-sm-5">
                <input type="email" class="form-control replace-hidden" name="email" value="${user.email}">
                <p class="form-control-static col-md-8 replace-show">${user.email}</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">账号类型</label>
            <div class="col-sm-5">
                <c:if test="${current.admin}">
                <select class="form-control replace-hidden" name="admin">
                    <option value="false" <c:if test="${!user.admin}">selected="selected"</c:if>>学生</option>
                    <option value="true" <c:if test="${user.admin}">selected="selected"</c:if>>管理员</option>
                </select>
                </c:if>
                <p class="form-control-static col-md-8 <c:if test="${current.admin}"> replace-show</c:if>">
                    <c:if test="${user.admin}">管理员</c:if>
                    <c:if test="${!user.admin}">学生</c:if>
                </p>
            </div>
        </div>
    </div>
</form>

<!-- reset_password_modal -->
<form class="modal fade form-horizontal bs-example-modal-sm" id="reset_password_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" action="/user/password" method="post">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">修改密码</h4>
            </div>
            <div class="modal-body" id="point_list">
                <input type="hidden" class="form-control" name="id" value="${user.id}">
                <div class="form-group">
                    <label class="col-sm-3 control-label">您的密码</label>
                    <div class="col-sm-9">
                        <input type="password" class="form-control" name="old_pass" placeholder="为了安全，请键入自己的旧密码">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">新密码</label>
                    <div class="col-sm-9">
                        <input type="password" class="form-control" name="new_pass" id="new_pass">
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="submit" class="btn btn-primary" >保存</button>
            </div>
        </div>
    </div>
</form>


<%@include file="base/footer.jsp" %>