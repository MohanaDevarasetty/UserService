package com.farmersbyte.enterprise.usermodule.onboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SetupLogService {

  @Autowired
  SetupLogRepository setupLogRepository;

  public boolean hashChanged(String contentType, String hash) {
    SetupLog setupLog = setupLogRepository.
            findFirstByContentTypeOrderByDeployedOnDesc(contentType);
    return !(setupLog != null && setupLog.getHash().equals(hash));
  }

  public void create(String contentType, String hash) {
    SetupLog setupLog = new SetupLog();
    setupLog.setContentType(contentType);
    setupLog.setHash(hash);
    setupLog.setDeployedOn(Instant.now());
    setupLogRepository.save(setupLog);
  }
}
