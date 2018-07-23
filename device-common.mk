#
# Copyright (C) 2018 CarbonRom
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

$(call inherit-product-if-exists, vendor/gapps/arm64/arm64-vendor.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/aosp_base_telephony.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/treble_common.mk)
$(call inherit-product, vendor/huawei/vndk/vndk.mk)

# Platform Path
PLATFORM_PATH := device/huawei/common

# Platform specific overlays
DEVICE_PACKAGE_OVERLAYS += $(PLATFORM_PATH)/overlay-common/main

# Device init scripts
PRODUCT_PACKAGES += \
    init.kirin.rc \
    init.kirin.environ.rc

BOARD_BUILD_DISABLED_VBMETAIMAGE := false


# Camera
PRODUCT_COPY_FILES += \
   $(PLATFORM_PATH)/camera/cam.hi3650.sh:system/bin/cam.hi3650.sh

# Display
PRODUCT_PACKAGES += \
    libion

# Codec
PRODUCT_COPY_FILES += \
    $(PLATFORM_PATH)/configs/media-codec-blacklist:system/etc/media-codec-blacklist

# Fingerprint
PRODUCT_BUILD_PROP_OVERRIDES += \
    PRIVATE_BUILD_DESC="BKL-L09-user 8.0.0 HUAWEIBKL-L09 130(C432) release-keys" \
    BUILD_FINGERPRINT="HONOR/BKL-L09/HWBKL:8.0.0/HUAWEIBKL-L09/130(C432):user/release-keys"
    
# GMS Client ID
PRODUCT_GMS_CLIENTID_BASE := android-huawei

# HIDL
PRODUCT_COPY_FILES += \
    $(PLATFORM_PATH)/compatibility_matrix.xml:system/compatibility_matrix.xml

PRODUCT_PACKAGES += \
    android.hidl.base@1.0 \
    android.hidl.manager@1.0
    
# Release tools
PRODUCT_COPY_FILES += \
    $(PLATFORM_PATH)/releasetools/releasetools.kirin.sh:system/bin/releasetools.kirin.sh
    
# Huawei Device Settings    
PRODUCT_PACKAGES += \
    HuaweiParts

# Huawei Doze
PRODUCT_PACKAGES += \
    HisiDoze

# Hotword fix
PRODUCT_PACKAGES += \
	HotwordEnrollmentOKGoogleHI6403 \
    HotwordEnrollmentXGoogleHI6403
       
# Input
PRODUCT_COPY_FILES += \
    $(PLATFORM_PATH)/keylayout/fingerprint.kl:system/usr/keylayout/fingerprint.kl

# Permissions
PRODUCT_COPY_FILES += \
    frameworks/native/data/etc/android.hardware.location.gps.xml:system/etc/permissions/android.hardware.location.gps.xml \
    frameworks/native/data/etc/android.software.midi.xml:system/etc/permissions/android.software.midi.xml \
    frameworks/native/data/etc/handheld_core_hardware.xml:system/etc/permissions/handheld_core_hardware.xml \
    frameworks/native/data/etc/android.hardware.fingerprint.xml:system/etc/permissions/android.hardware.fingerprint.xml \
    frameworks/native/data/etc/android.hardware.telephony.gsm.xml:system/etc/permissions/android.hardware.telephony.gsm.xml

# Freeform Multiwindow
PRODUCT_COPY_FILES += \
    frameworks/native/data/etc/android.software.freeform_window_management.xml:system/etc/permissions/android.software.freeform_window_management.xml
    
# Radio
PRODUCT_PACKAGES += \
    qti-telephony-common

PRODUCT_BOOT_JARS += \
    telephony-ext
    
# APNs
PRODUCT_COPY_FILES += \
    $(PLATFORM_PATH)/ril/apns-full-conf.xml:system/etc/apns-conf.xml

# Resize system
PRODUCT_COPY_FILES += \
    $(PLATFORM_PATH)/rw-system.sh:system/bin/rw-system.sh

# Shims
PRODUCT_PACKAGES += \
    libshims_hwsmartdisplay_jni \
    libshims_hisupl
    
# HW Framework
PRODUCT_PACKAGES += \
	com.huawei.audioalgo \
	hwpostcamera \
	hwEmui

# TextClassifier
PRODUCT_PACKAGES += \
    textclassifier.smartselection.bundle1
    
# Overlays
PRODUCT_PACKAGES += \
    openkirin-overlay-burnin \
    openkirin-overlay-notch

# Override device name
PRODUCT_SYSTEM_DEFAULT_PROPERTIES += \
    ro.build.version.sdk=$(PLATFORM_SDK_VERSION) \
    ro.build.version.codename=$(PLATFORM_VERSION_CODENAME) \
    ro.build.version.all_codenames=$(PLATFORM_VERSION_ALL_CODENAMES) \
    ro.build.version.huawei=8.0.0 \
    ro.build.version.release=$(PLATFORM_VERSION) \
    ro.cust.cdrom=/dev/null

# Sdcardfs    
PRODUCT_SYSTEM_DEFAULT_PROPERTIES += \
    ro.sys.sdcardfs=0 \
    persist.sys.sdcardfs.emulated=0 \
    persist.sys.sdcardfs.public=0
    
# Disable excessive dalvik debug messages
PRODUCT_PROPERTY_OVERRIDES += \
    dalvik.vm.debug.alloc=0
    
# Huawei HiSuite (also other OEM custom programs I guess) it's of no use in AOSP builds
PRODUCT_SYSTEM_DEFAULT_PROPERTIES += \
	persist.sys.usb.config=adb

# USB Audio
PRODUCT_COPY_FILES += \
    frameworks/av/services/audiopolicy/config/usb_audio_policy_configuration.xml:system/etc/usb_audio_policy_configuration.xml
    
# VNDK
PRODUCT_COPY_FILES += \
    $(PLATFORM_PATH)/vndk/vndk-detect:system/bin/vndk-detect \
    $(PLATFORM_PATH)/vndk/vndk.rc:system/etc/init/vndk.rc \
    $(PLATFORM_PATH)/vndk/ld.config.26.txt:system/etc/ld.config.26.txt \
    $(PLATFORM_PATH)/vndk/ld.config.27.txt:system/etc/ld.config.27.txt \
    $(PLATFORM_PATH)/vndk/ld.config.hi3650.txt:system/etc/ld.config.hi3650.txt
    
# GPS
PRODUCT_COPY_FILES += \
	 $(PLATFORM_PATH)/configs/gps.conf:system/etc/gps.conf \
	 $(PLATFORM_PATH)/configs/gps.conf:system/etc/gps_debug.conf
