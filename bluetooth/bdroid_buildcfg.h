#ifndef _BDROID_BUILDCFG_H
#define _BDROID_BUILDCFG_H

#include <cutils/properties.h>
#include <string.h>

static inline const char* BtmDefLocalName()
{
    static char product_device[PROPERTY_VALUE_MAX];
    property_get("ro.config.marketing_name", product_device, "");

    if (strcmp(product_device, "") != 0)
        return product_device;

    return "";
}

#define BTM_BYPASS_EXTRA_ACL_SETUP TRUE
#define BTM_DEF_LOCAL_NAME BtmDefLocalName()

#define BTM_WBS_INCLUDED TRUE
#define BTIF_HF_WBS_PREFERRED TRUE

#define BLE_VND_INCLUDED TRUE
#undef PROPERTY_VALUE_MAX
#endif
