package store.server.item.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.SneakyThrows;
import org.hibernate.validator.constraints.Length;
import store.server.category.domain.Category;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "item")
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String name;

    @Length(min = 5)
    private String description;

    @Valid
    @NotNull
    @Embedded
    private Price price;

    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
    private Date postDate;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<@NotNull Category> categories = new HashSet<>();

    @JsonIgnore
    public static transient final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public String getPostDate() {
        return dateFormat.format(postDate);
    }

    @SneakyThrows
    public void setPostDate(String postDate) {
        this.postDate = dateFormat.parse(postDate);
    }

    @JsonIgnore
    public Date getUnformattedPostDate() {
        return postDate;
    }

    public void initializePostDate() {
        this.postDate = new Date();
    }

}
