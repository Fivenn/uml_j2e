<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%
	String connectionNotValid = (String)request.getAttribute("connectionNotValid");
%>
        <!DOCTYPE html>
        <html>

        <head>
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
            <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
            <title>Connection page</title>
            <link type="text/css" rel="stylesheet" href="style.css" />
        </head>

        <body>
            <div class="container">
                <div class="row justify-content-center align-items-center" style="height:100vh">
                    <div class="col-4">
                        <div class="card">
                            <div class="card-body">
                                <form action="Connection" method="post">
                                    <h2>Bienvenue sur Days Off Manager</h2>
                                    <div class="form-group">
                                        <input placeholder="Email" type="text" class="form-control" name="email">

                                    </div>
                                    <div class="form-group">
                                        <input placeholder="Password" type="password" class="form-control" name="password">

                                    </div>
                                    <button type="submit" class="btn btn-primary">Envoyer</button>
                                </form>
                                <% if(connectionNotValid != null){ %>
                                    <p>
                                        <%=connectionNotValid%>
                                    </p>
                                    <% }%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </body>

        </html>