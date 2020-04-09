package com.hxl.vo;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: hanxuanliang
 * @Date: 2020/3/16 12:33
 */
@Setter
@Getter
@ToString
public class PagingVO<T, K> extends Paging<T> {

    public PagingVO(Page<T> pageT, Class<K> classK) {
        initPageParameters(pageT);

        List<T> tList = pageT.getContent();

        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List<K> voList = new ArrayList<>();
        tList.forEach(t -> {
            // map(source, target)
            K vo = mapper.map(t, classK);
            voList.add(vo);
        });
        setItems(voList);
    }
}
