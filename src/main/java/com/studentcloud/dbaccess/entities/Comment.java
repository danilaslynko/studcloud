package com.studentcloud.dbaccess.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = { CascadeType.REFRESH, CascadeType.PERSIST }
    )
    @JoinColumn(name = "file_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Отсутствует файл")
    private File file;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String userName;

    @NotBlank(message = "Сообщение не может быть пустым")
    private String message;

    private Date date;

    public Comment() {}

    public Comment(File file, String userName, String message, Date date) {
        this.file = file;
        this.userName = userName;
        this.message = message;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String comment) {
        this.message = comment;
    }
}
