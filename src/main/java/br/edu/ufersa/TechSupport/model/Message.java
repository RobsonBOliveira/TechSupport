package br.edu.ufersa.TechSupport.model;


import jakarta.persistence.*;

@Entity
@Table(name = "tb_messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ticketId;
    private String senderName;
    private String content;

    public Message() {
    }

    public Message(Long ticketId, String senderName, String content) {
        this.ticketId = ticketId;
        this.senderName = senderName;
        this.content = content;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
