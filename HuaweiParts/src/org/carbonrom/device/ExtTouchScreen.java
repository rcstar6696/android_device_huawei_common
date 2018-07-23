/*
 * Copyright (C) 2018 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vendor.huawei.hardware.tp.V1_0;

import android.os.HwBinder;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.RemoteException;

public class ExtTouchScreen {

    private static final String DESCRIPTOR =
            "vendor.huawei.hardware.tp@1.0::ITouchscreen";
    private static final int TRANSACTION_hwTsSetGloveMode = 1;
    private static final int TRANSACTION_hwTsSetCoverMode = 2;

    private static IHwBinder sTouchscreen;

    public ExtTouchScreen() throws RemoteException {
        sTouchscreen = HwBinder.getService(DESCRIPTOR, "default");
    }

    public boolean hwTsSetCoverMode(boolean enable) {
        if (sTouchscreen == null) {
            return false;
        }

        HwParcel data = new HwParcel();
        HwParcel reply = new HwParcel();

        try {
            data.writeInterfaceToken(DESCRIPTOR);
            data.writeBool(enable);

            sTouchscreen.transact(TRANSACTION_hwTsSetGloveMode, data, reply, 0);

            reply.verifySuccess();
            data.releaseTemporaryStorage();

            return reply.readBool();
        } catch (Throwable t) {
            return false;
        } finally {
            reply.release();
        }
    }
}
