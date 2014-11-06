/*
 * Copyright 2014 Higher Frequency Trading http://www.higherfrequencytrading.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.openhft.chronicle.hash.replication;

import org.jetbrains.annotations.Nullable;

class AbstractReplication {
    protected final byte localIdentifier;
    protected final UdpConfig udpConfig;
    protected final TcpConfig tcpConfig;

    AbstractReplication(byte localIdentifier, Builder builder) {
        udpConfig = builder.udpConfig;
        this.localIdentifier = localIdentifier;
        tcpConfig = builder.tcpConfig;
    }

    public byte identifier() {
        return localIdentifier;
    }

    @Nullable
    public TcpConfig tcpTransportAndNetwork() {
        return tcpConfig;
    }

    @Nullable
    public UdpConfig udpTransport() {
        return udpConfig;
    }

    static abstract class Builder<B extends Builder> {
        protected UdpConfig udpConfig = null;
        protected TcpConfig tcpConfig = null;

        public B tcpTransportAndNetwork(TcpConfig tcpConfig) {
            this.tcpConfig = tcpConfig;
            return (B) this;
        }

        public B udpTransport(UdpConfig udpConfig) {
            this.udpConfig = udpConfig;
            return (B) this;
        }

        void check() {
            if (udpConfig == null && tcpConfig == null)
                throw new IllegalStateException(
                        "At least one transport method (TCP or UDP) should be configured");
        }
    }
}
