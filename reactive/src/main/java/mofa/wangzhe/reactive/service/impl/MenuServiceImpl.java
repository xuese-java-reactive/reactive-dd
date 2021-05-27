package mofa.wangzhe.reactive.service.impl;

import mofa.wangzhe.reactive.mapper.MenuMapper;
import mofa.wangzhe.reactive.model.MenuModel;
import mofa.wangzhe.reactive.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * @author LD
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {

//    @Autowired
//    private MenuMapper mapper;

    @Override
    public Mono<MenuModel> save(MenuModel model) {
//        return mapper.save(model);
        return null;
    }
}
