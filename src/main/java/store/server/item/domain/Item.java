package store.server.item.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.SneakyThrows;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@Table(name = "item")
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @Length(min = 5)
    private String description;

    @NotNull
    @Embedded
    private Price price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
    private Date postDate;

    private boolean onSale;

    @JsonIgnore
    public static transient final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public String getPostDate() {
        return dateFormat.format(postDate);
    }

    @SneakyThrows
    public void setPostDate(String postDate) {
        this.postDate = dateFormat.parse(postDate);
    }

    public Date getUnformattedPostDate() {
        return postDate;
    }

    public void initializePostDate() {
        this.postDate = new Date();
    }

}
