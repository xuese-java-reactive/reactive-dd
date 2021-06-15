package mofa.wangzhe.reactive.service.impl;

import com.auth0.jwt.interfaces.Claim;
import mofa.wangzhe.reactive.model.JurModel;
import mofa.wangzhe.reactive.model.MenuModel;
import mofa.wangzhe.reactive.service.MenuService;
import mofa.wangzhe.reactive.util.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
    private R2dbcEntityTemplate template;

    @Override
    public Mono<MenuModel> save(MenuModel model) {
        model.setUuid(UUID.randomUUID().toString());
        return template.insert(model)
                .switchIfEmpty(Mono.error(new Exception("未查询到数据")));
    }

    @Override
    public Mono<MenuModel> remove(MenuModel model) {
        return template.select(MenuModel.class)
                .matching(Query.query(Criteria.where("pid").is(model.getUuid())))
                .count()
                .flatMap(f -> {
                    long i = 0;
                    if (f > i) {
                        return Mono.error(new Exception("请先删除下级"));
                    } else {
                        return template.delete(model);
                    }
                });
    }

    @Override
    public Mono<MenuModel> update(MenuModel model) {
        return template.selectOne(Query.query(Criteria.where("uuid").is(model.getUuid())), MenuModel.class)
                .flatMap(f -> {
                    if (StringUtils.hasText(model.getName())) {
                        f.setName(model.getName());
                    }
                    if (StringUtils.hasText(model.getPid())) {
                        f.setPid(model.getPid());
                    }
                    return template.update(f)
                            .switchIfEmpty(Mono.error(new Exception("未查询到数据")));
                });
    }

    @Override
    public Flux<MenuModel> findAll(String pid) {
        return template.select(MenuModel.class)
                .matching(Query.empty().sort(Sort.by(Sort.Order.asc("orders"))))
                .all();
    }

    @Override
    public Flux<MenuModel> findAll2(String auth) {
        Claim aud = JwtUtil.getClaim(auth, "aud");
        if (aud == null) {
            return Flux.error(new Exception("令牌解析错误,可以尝试刷新后从新请求"));
        }
        return template.select(JurModel.class)
                .matching(Query.query(Criteria.where("acc_id").is(aud.asString())))
                .all()
                .flatMap(f -> template.select(Query.query(
                        Criteria.where("uuid").in(f.getMenuId())), MenuModel.class
                ));
    }
}
