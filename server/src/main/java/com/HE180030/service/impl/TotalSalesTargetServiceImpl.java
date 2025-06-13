package com.HE180030.service.impl;

import com.HE180030.model.TotalSalesTarget;
import com.HE180030.repository.TotalSalesTargetRepository;
import com.HE180030.service.TotalSalesTargetService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TotalSalesTargetServiceImpl implements TotalSalesTargetService {
    TotalSalesTargetRepository repo;
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void deleteTotalSalesTargetByAccountID(int id) {
        logger.info("Deleting total sales target by account ID: " + id);
        List<TotalSalesTarget> targets = repo.findByAccountId(id);
        targets.forEach(target -> {
            target.setAccount(null);
            repo.delete(target);
        });
    }
}
