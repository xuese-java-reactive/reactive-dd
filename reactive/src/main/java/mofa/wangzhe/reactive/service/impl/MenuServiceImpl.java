package mofa.wangzhe.reactive.service.impl;

import io.r2dbc.spi.ConnectionFactory;
import mofa.wangzhe.reactive.model.MenuModel;
import mofa.wangzhe.reactive.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * 参考https://zhuanlan.zhihu.com/p/366456122
 *
 * @author LD
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public Mono<MenuModel> save(MenuModel model) {
        model.setUuid(UUID.randomUUID().toString());
        R2dbcEntityTemplate template = new R2dbcEntityTemplate(connectionFactory);
        return template.insert(MenuModel.class)
                .into("menu_table")
                .using(model)
                .switchIfEmpty(Mono.error(new Exception("参数为空")));
    }

    @Override
    public Flux<MenuModel> findAll(String pid) {
        R2dbcEntityTemplate template = new R2dbcEntityTemplate(connectionFactory);
        return template.select(MenuModel.class)
                .matching(Query.empty().sort(Sort.by(Sort.Order.asc("p_id"))))
                .all();
    }
}
