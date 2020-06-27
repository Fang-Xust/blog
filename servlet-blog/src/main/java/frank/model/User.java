package frank.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class User {

    private Integer id;

    private String name;

    //使用的是java.util.Date
    private Date createTime;
}
