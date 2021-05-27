package mofa.wangzhe.reactive.service.impl;

import io.r2dbc.spi.ConnectionFactory;
import mofa.wangzhe.reactive.model.MenuModel;
import mofa.wangzhe.reactive.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author LD
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public Mono<MenuModel> save(MenuModel model) {
//        return mapper.save(model);
        return Mono.empty();
    }

    @Override
    public Flux<MenuModel> findAll(String pId) {
        R2dbcEntityTemplate template = new R2dbcEntityTemplate(connectionFactory);
        return template.select(MenuModel.class)
                .all();
    }
}
