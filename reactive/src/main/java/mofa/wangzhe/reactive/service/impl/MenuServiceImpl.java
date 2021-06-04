
package mofa.wangzhe.reactive.service.impl;

import mofa.wangzhe.reactive.model.MenuModel;
import mofa.wangzhe.reactive.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.CriteriaDefinition;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 *
 *
 * @author LD
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private R2dbcEntityTemplate template;

    @Override
    public Mono<MenuModel> save(MenuModel model) {
        model.setUuid(UUID.randomUUID().toString());
        return template.insert(model)
                .switchIfEmpty(Mono.error(new Exception("参数为空")));
    }

    @Override
    public Mono<MenuModel> remove(MenuModel model) {
        return template.delete(model);
    }

    @Override
    public Mono<MenuModel> update(MenuModel model) {
        return template.update(model)
                .switchIfEmpty(Mono.error(new Exception("参数为空")));
    }

    @Override
    public Flux<MenuModel> page(int pageSize, int pageNum, String search) {

        Query query;
        if (StringUtils.hasText(search)) {
            query = Query.query(Criteria.where("uuid").like("%"+search+"%"));
        } else {
            query = Query.query(CriteriaDefinition.empty());
        }
        query = query.with(PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "uuid")));

        return template.select(query, MenuModel.class);
    }

    @Override
    public Mono<Long> count(String search) {
        Query query;
        if (StringUtils.hasText(search)) {
            query = Query.query(Criteria.where("uuid").like("%"+search+"%"));
        } else {
            query = Query.query(CriteriaDefinition.empty());
        }
        return template.select(query, MenuModel.class)
                .count();
    }
}
