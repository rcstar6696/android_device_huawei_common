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

# Platform Path
PLATFORM_PATH := device/huawei/common

# Generic A-only Target
include build/make/target/board/generic_arm64_a/BoardConfig.mk

TARGET_RECOVERY_FSTAB := $(PLATFORM_PATH)/rootdir/etc/fstab.kirin

# Kernel
BOARD_KERNEL_IMAGE_NAME := Image
TARGET_NO_KERNEL := true
PRODUCT_FULL_TREBLE := true
BOARD_VNDK_VERSION := current


# Platform
TARGET_ARCH := arm64
TARGET_ARCH_VARIANT := armv8-a
TARGET_CPU_ABI := arm64-v8a
TARGET_CPU_ABI2 :=
TARGET_CPU_VARIANT := cortex-a53

TARGET_2ND_ARCH := arm
TARGET_2ND_ARCH_VARIANT := armv7-a-neon
TARGET_2ND_CPU_ABI := armeabi-v7a
TARGET_2ND_CPU_ABI2 := armeabi
TARGET_2ND_CPU_VARIANT := cortex-a53

# Kernel
BOARD_KERNEL_BASE := 0x00078000
BOARD_KERNEL_CMDLINE := loglevel=4 initcall_debug=n slub_min_objects=16 page_tracker=on unmovable_isolate1=2:192M,3:224M,4:256M printktimer=0xfff0a000,0x534,0x538 androidboot.selinux=permissive
BOARD_KERNEL_IMAGE_NAME := Image.gz
BOARD_KERNEL_OFFSET := 0x00008000
BOARD_KERNEL_OS_PATCH_LEVEL := 2018-05-05
BOARD_KERNEL_OS_VERSION := 8.0.0
BOARD_KERNEL_PAGESIZE := 2048
BOARD_KERNEL_SECOND_OFFSET := 0x00e88000
BOARD_KERNEL_TAGS_OFFSET := 0x07988000
BOARD_RAMDISK_OFFSET := 0x07b88000
TARGET_KERNEL_TOOLCHAIN_ROOT := prebuilts/gcc/linux-x86/aarch64/aarch64-linux-android-4.9/bin
TARGET_KERNEL_TOOLS_PREFIX := $(TARGET_KERNEL_TOOLCHAIN_ROOT)/bin/aarch64-linux-android-

# Bluetooth
BOARD_BLUETOOTH_BDROID_BUILDCFG_INCLUDE_DIR := $(PLATFORM_PATH)/bluetooth
BOARD_HAVE_BLUETOOTH := true

# Charger 
BOARD_CHARGER_DISABLE_INIT_BLANK := true
BACKLIGHT_PATH := "/sys/class/leds/lcd_backlight0/brightness"
HEALTHD_BACKLIGHT_LEVEL := 102
HEALTHD_FORCE_BACKLIGHT_CONTROL := true

# Charge Detection
HEALTHD_ENABLE_HUAWEI_FASTCHG_CHECK := true

# Extended Filesystem Support
TARGET_EXFAT_DRIVER := exfat
  
# Display
TARGET_USES_HWC2 := true

# Binder
TARGET_USES_64_BIT_BINDER := true

# Cpuset
ENABLE_CPUSETS := true

# Schedboost
ENABLE_SCHEDBOOST := true

# Graphics
NUM_FRAMEBUFFER_SURFACE_BUFFERS := 3
OVERRIDE_RS_DRIVER := libRSDriverArm.so
HWUI_COMPILE_SYMBOLS := true

# Audio
BOARD_USES_ALSA_AUDIO := true

BOARD_VNDK_RUNTIME_DISABLE := true
BOARD_USE_LEGACY_UI := true

# Properties
TARGET_SYSTEM_PROP := $(PLATFORM_PATH)/system.prop

# Sepolicy
BOARD_PLAT_PRIVATE_SEPOLICY_DIR += $(PLATFORM_PATH)/sepolicy/private
BOARD_PLAT_PUBLIC_SEPOLICY_DIR += $(PLATFORM_PATH)/sepolicy/public

# Shims
TARGET_LD_SHIM_LIBS := \
    /system/lib64/libdisplayenginesvc_1_0.so|libshims_hwsmartdisplay_jni.so \
    /system/lib64/libdisplayenginesvc_1_1.so|libshims_hwsmartdisplay_jni.so \
    /system/lib64/libhwpwmanager_jni.so|libshims_hwsmartdisplay_jni.so \
    /system/lib64/libhwsmartdisplay_jni.so|libshims_hwsmartdisplay_jni.so

# System size
BOARD_KERNELIMAGE_PARTITION_SIZE := 15800132
BOARD_RAMDISKIMAGE_PARTITION_SIZE := 10533422
BOARD_SYSTEMIMAGE_PARTITION_SIZE := 2000000000	# 2.0 GB
