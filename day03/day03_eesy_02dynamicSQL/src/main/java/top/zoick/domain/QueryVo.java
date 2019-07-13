package top.zoick.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zoick
 * @date 2019/7/8 23:12
 */
@Getter
@Setter
public class QueryVo {

    private User user;
    private List<Integer> ids;
}
