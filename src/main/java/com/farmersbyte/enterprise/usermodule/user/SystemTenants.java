package com.farmersbyte.enterprise.usermodule.user;

import java.util.HashMap;
import java.util.Map;

public final class SystemTenants {

  // Mother Tenant
  private static final String Platform = "000000000000000000000000";
  // 1 Series - Direct
  private static final String FarmersByteDirect = "111111111111111111111111";
  private static final String MinersByteDirect = "111111111111111111111112";
  private static final String ChefsByteDirect = "111111111111111111111113";
  private static final String JewellersByteDirect = "111111111111111111111114";
  // 1 Series - Enterprise
  private static final String FarmersByteEnterprise = "222222222222222222222221";
  private static final String MinersByteEnterprise = "222222222222222222222222";
  private static final String ChefsByteEnterprise = "222222222222222222222223";
  // 1 Series - Elevate
  private static final String FarmersByteElevate = "333333333333333333333331";
  private static final String MinersByteElevate = "333333333333333333333332";
  private static final String ChefsByteElevate = "333333333333333333333333";

  public static final String getSystemTenant(String applicationName) {
    Map<String, String> tenants = new HashMap<>();
    tenants.put("Platform", Platform);
    tenants.put("FarmersByteDirect", FarmersByteDirect);
    tenants.put("MinersByteDirect", MinersByteDirect);
    tenants.put("ChefsByteDirect", ChefsByteDirect);
    tenants.put("JewellersByteDirect", JewellersByteDirect);
    tenants.put("FarmersByteEnterprise", FarmersByteEnterprise);
    tenants.put("MinersByteEnterprise", MinersByteEnterprise);
    tenants.put("ChefsByteEnterprise", ChefsByteEnterprise);
    tenants.put("FarmersByteElevate", FarmersByteElevate);
    tenants.put("MinersByteElevate", MinersByteElevate);
    tenants.put("ChefsByteElevate", ChefsByteElevate);
    return tenants.get(applicationName);
  }

}