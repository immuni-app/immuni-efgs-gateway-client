/*-
 *   Copyright (C) 2020 Presidenza del Consiglio dei Ministri.
 *   Please refer to the AUTHORS file for more information. 
 *   This program is free software: you can redistribute it and/or modify 
 *   it under the terms of the GNU Affero General Public License as 
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *   This program is distributed in the hope that it will be useful, 
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of 
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 *   GNU Affero General Public License for more details.
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program. If not, see <https://www.gnu.org/licenses/>.   
 */
package it.interop.federationgateway.model;

public final class EfgsProto {
	private EfgsProto() {
	}

	public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {
	}

	public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
		registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
	}

	/**
	 * Protobuf enum {@code eu.interop.ReportType}
	 */
	public enum ReportType implements com.google.protobuf.ProtocolMessageEnum {
		/**
		 * <code>UNKNOWN = 0;</code>
		 */
		UNKNOWN(0),
		/**
		 * <code>CONFIRMED_TEST = 1;</code>
		 */
		CONFIRMED_TEST(1),
		/**
		 * <code>CONFIRMED_CLINICAL_DIAGNOSIS = 2;</code>
		 */
		CONFIRMED_CLINICAL_DIAGNOSIS(2),
		/**
		 * <code>SELF_REPORT = 3;</code>
		 */
		SELF_REPORT(3),
		/**
		 * <code>RECURSIVE = 4;</code>
		 */
		RECURSIVE(4),
		/**
		 * <code>REVOKED = 5;</code>
		 */
		REVOKED(5), UNRECOGNIZED(-1),;

		/**
		 * <code>UNKNOWN = 0;</code>
		 */
		public static final int UNKNOWN_VALUE = 0;
		/**
		 * <code>CONFIRMED_TEST = 1;</code>
		 */
		public static final int CONFIRMED_TEST_VALUE = 1;
		/**
		 * <code>CONFIRMED_CLINICAL_DIAGNOSIS = 2;</code>
		 */
		public static final int CONFIRMED_CLINICAL_DIAGNOSIS_VALUE = 2;
		/**
		 * <code>SELF_REPORT = 3;</code>
		 */
		public static final int SELF_REPORT_VALUE = 3;
		/**
		 * <code>RECURSIVE = 4;</code>
		 */
		public static final int RECURSIVE_VALUE = 4;
		/**
		 * <code>REVOKED = 5;</code>
		 */
		public static final int REVOKED_VALUE = 5;

		public final int getNumber() {
			if (this == UNRECOGNIZED) {
				throw new java.lang.IllegalArgumentException("Can't get the number of an unknown enum value.");
			}
			return value;
		}

		/**
		 * @param value The numeric wire value of the corresponding enum entry.
		 * @return The enum associated with the given numeric wire value.
		 * @deprecated Use {@link #forNumber(int)} instead.
		 */
		@java.lang.Deprecated
		public static ReportType valueOf(int value) {
			return forNumber(value);
		}

		/**
		 * @param value The numeric wire value of the corresponding enum entry.
		 * @return The enum associated with the given numeric wire value.
		 */
		public static ReportType forNumber(int value) {
			switch (value) {
			case 0:
				return UNKNOWN;
			case 1:
				return CONFIRMED_TEST;
			case 2:
				return CONFIRMED_CLINICAL_DIAGNOSIS;
			case 3:
				return SELF_REPORT;
			case 4:
				return RECURSIVE;
			case 5:
				return REVOKED;
			default:
				return null;
			}
		}

		public static com.google.protobuf.Internal.EnumLiteMap<ReportType> internalGetValueMap() {
			return internalValueMap;
		}

		private static final com.google.protobuf.Internal.EnumLiteMap<ReportType> internalValueMap = new com.google.protobuf.Internal.EnumLiteMap<ReportType>() {
			public ReportType findValueByNumber(int number) {
				return ReportType.forNumber(number);
			}
		};

		public final com.google.protobuf.Descriptors.EnumValueDescriptor getValueDescriptor() {
			if (this == UNRECOGNIZED) {
				throw new java.lang.IllegalStateException("Can't get the descriptor of an unrecognized enum value.");
			}
			return getDescriptor().getValues().get(ordinal());
		}

		public final com.google.protobuf.Descriptors.EnumDescriptor getDescriptorForType() {
			return getDescriptor();
		}

		public static final com.google.protobuf.Descriptors.EnumDescriptor getDescriptor() {
			return it.interop.federationgateway.model.EfgsProto.getDescriptor().getEnumTypes().get(0);
		}

		private static final ReportType[] VALUES = values();

		public static ReportType valueOf(com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
			if (desc.getType() != getDescriptor()) {
				throw new java.lang.IllegalArgumentException("EnumValueDescriptor is not for this type.");
			}
			if (desc.getIndex() == -1) {
				return UNRECOGNIZED;
			}
			return VALUES[desc.getIndex()];
		}

		private final int value;

		private ReportType(int value) {
			this.value = value;
		}

		// @@protoc_insertion_point(enum_scope:eu.interop.ReportType)
	}

	public interface DiagnosisKeyBatchOrBuilder extends
			// @@protoc_insertion_point(interface_extends:eu.interop.DiagnosisKeyBatch)
			com.google.protobuf.MessageOrBuilder {

		/**
		 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
		 */
		java.util.List<it.interop.federationgateway.model.EfgsProto.DiagnosisKey> getKeysList();

		/**
		 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
		 */
		it.interop.federationgateway.model.EfgsProto.DiagnosisKey getKeys(int index);

		/**
		 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
		 */
		int getKeysCount();

		/**
		 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
		 */
		java.util.List<? extends it.interop.federationgateway.model.EfgsProto.DiagnosisKeyOrBuilder> getKeysOrBuilderList();

		/**
		 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
		 */
		it.interop.federationgateway.model.EfgsProto.DiagnosisKeyOrBuilder getKeysOrBuilder(int index);
	}

	/**
	 * Protobuf type {@code eu.interop.DiagnosisKeyBatch}
	 */
	public static final class DiagnosisKeyBatch extends com.google.protobuf.GeneratedMessageV3 implements
			// @@protoc_insertion_point(message_implements:eu.interop.DiagnosisKeyBatch)
			DiagnosisKeyBatchOrBuilder {
		private static final long serialVersionUID = 0L;

		// Use DiagnosisKeyBatch.newBuilder() to construct.
		private DiagnosisKeyBatch(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
			super(builder);
		}

		private DiagnosisKeyBatch() {
			keys_ = java.util.Collections.emptyList();
		}

		@java.lang.Override
		@SuppressWarnings({ "unused" })
		protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
			return new DiagnosisKeyBatch();
		}

		@java.lang.Override
		public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
			return this.unknownFields;
		}

		private DiagnosisKeyBatch(com.google.protobuf.CodedInputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			this();
			if (extensionRegistry == null) {
				throw new java.lang.NullPointerException();
			}
			int mutable_bitField0_ = 0;
			com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet
					.newBuilder();
			try {
				boolean done = false;
				while (!done) {
					int tag = input.readTag();
					switch (tag) {
					case 0:
						done = true;
						break;
					case 10: {
						if (!((mutable_bitField0_ & 0x00000001) != 0)) {
							keys_ = new java.util.ArrayList<it.interop.federationgateway.model.EfgsProto.DiagnosisKey>();
							mutable_bitField0_ |= 0x00000001;
						}
						keys_.add(input.readMessage(it.interop.federationgateway.model.EfgsProto.DiagnosisKey.parser(),
								extensionRegistry));
						break;
					}
					default: {
						if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
							done = true;
						}
						break;
					}
					}
				}
			} catch (com.google.protobuf.InvalidProtocolBufferException e) {
				throw e.setUnfinishedMessage(this);
			} catch (java.io.IOException e) {
				throw new com.google.protobuf.InvalidProtocolBufferException(e).setUnfinishedMessage(this);
			} finally {
				if (((mutable_bitField0_ & 0x00000001) != 0)) {
					keys_ = java.util.Collections.unmodifiableList(keys_);
				}
				this.unknownFields = unknownFields.build();
				makeExtensionsImmutable();
			}
		}

		public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
			return it.interop.federationgateway.model.EfgsProto.internal_static_eu_interop_DiagnosisKeyBatch_descriptor;
		}

		@java.lang.Override
		protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
			return it.interop.federationgateway.model.EfgsProto.internal_static_eu_interop_DiagnosisKeyBatch_fieldAccessorTable
					.ensureFieldAccessorsInitialized(
							it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch.class,
							it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch.Builder.class);
		}

		public static final int KEYS_FIELD_NUMBER = 1;
		private java.util.List<it.interop.federationgateway.model.EfgsProto.DiagnosisKey> keys_;

		/**
		 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
		 */
		@java.lang.Override
		public java.util.List<it.interop.federationgateway.model.EfgsProto.DiagnosisKey> getKeysList() {
			return keys_;
		}

		/**
		 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
		 */
		@java.lang.Override
		public java.util.List<? extends it.interop.federationgateway.model.EfgsProto.DiagnosisKeyOrBuilder> getKeysOrBuilderList() {
			return keys_;
		}

		/**
		 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
		 */
		@java.lang.Override
		public int getKeysCount() {
			return keys_.size();
		}

		/**
		 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
		 */
		@java.lang.Override
		public it.interop.federationgateway.model.EfgsProto.DiagnosisKey getKeys(int index) {
			return keys_.get(index);
		}

		/**
		 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
		 */
		@java.lang.Override
		public it.interop.federationgateway.model.EfgsProto.DiagnosisKeyOrBuilder getKeysOrBuilder(int index) {
			return keys_.get(index);
		}

		private byte memoizedIsInitialized = -1;

		@java.lang.Override
		public final boolean isInitialized() {
			byte isInitialized = memoizedIsInitialized;
			if (isInitialized == 1)
				return true;
			if (isInitialized == 0)
				return false;

			memoizedIsInitialized = 1;
			return true;
		}

		@java.lang.Override
		public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
			for (int i = 0; i < keys_.size(); i++) {
				output.writeMessage(1, keys_.get(i));
			}
			unknownFields.writeTo(output);
		}

		@java.lang.Override
		public int getSerializedSize() {
			int size = memoizedSize;
			if (size != -1)
				return size;

			size = 0;
			for (int i = 0; i < keys_.size(); i++) {
				size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, keys_.get(i));
			}
			size += unknownFields.getSerializedSize();
			memoizedSize = size;
			return size;
		}

		@java.lang.Override
		public boolean equals(final java.lang.Object obj) {
			if (obj == this) {
				return true;
			}
			if (!(obj instanceof it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch)) {
				return super.equals(obj);
			}
			it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch other = (it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch) obj;

			if (!getKeysList().equals(other.getKeysList()))
				return false;
			if (!unknownFields.equals(other.unknownFields))
				return false;
			return true;
		}

		@java.lang.Override
		public int hashCode() {
			if (memoizedHashCode != 0) {
				return memoizedHashCode;
			}
			int hash = 41;
			hash = (19 * hash) + getDescriptor().hashCode();
			if (getKeysCount() > 0) {
				hash = (37 * hash) + KEYS_FIELD_NUMBER;
				hash = (53 * hash) + getKeysList().hashCode();
			}
			hash = (29 * hash) + unknownFields.hashCode();
			memoizedHashCode = hash;
			return hash;
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch parseFrom(java.nio.ByteBuffer data)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch parseFrom(java.nio.ByteBuffer data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch parseFrom(
				com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch parseFrom(
				com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch parseFrom(byte[] data)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch parseFrom(byte[] data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch parseFrom(
				java.io.InputStream input) throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch parseFrom(
				java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch parseDelimitedFrom(
				java.io.InputStream input) throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch parseDelimitedFrom(
				java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input,
					extensionRegistry);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch parseFrom(
				com.google.protobuf.CodedInputStream input) throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch parseFrom(
				com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
		}

		@java.lang.Override
		public Builder newBuilderForType() {
			return newBuilder();
		}

		public static Builder newBuilder() {
			return DEFAULT_INSTANCE.toBuilder();
		}

		public static Builder newBuilder(it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch prototype) {
			return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
		}

		@java.lang.Override
		public Builder toBuilder() {
			return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
		}

		@java.lang.Override
		protected Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
			Builder builder = new Builder(parent);
			return builder;
		}

		/**
		 * Protobuf type {@code eu.interop.DiagnosisKeyBatch}
		 */
		public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
				// @@protoc_insertion_point(builder_implements:eu.interop.DiagnosisKeyBatch)
				it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatchOrBuilder {
			public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
				return it.interop.federationgateway.model.EfgsProto.internal_static_eu_interop_DiagnosisKeyBatch_descriptor;
			}

			@java.lang.Override
			protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
				return it.interop.federationgateway.model.EfgsProto.internal_static_eu_interop_DiagnosisKeyBatch_fieldAccessorTable
						.ensureFieldAccessorsInitialized(
								it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch.class,
								it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch.Builder.class);
			}

			// Construct using
			// it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch.newBuilder()
			private Builder() {
				maybeForceBuilderInitialization();
			}

			private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
				super(parent);
				maybeForceBuilderInitialization();
			}

			private void maybeForceBuilderInitialization() {
				if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
					getKeysFieldBuilder();
				}
			}

			@java.lang.Override
			public Builder clear() {
				super.clear();
				if (keysBuilder_ == null) {
					keys_ = java.util.Collections.emptyList();
					bitField0_ = (bitField0_ & ~0x00000001);
				} else {
					keysBuilder_.clear();
				}
				return this;
			}

			@java.lang.Override
			public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
				return it.interop.federationgateway.model.EfgsProto.internal_static_eu_interop_DiagnosisKeyBatch_descriptor;
			}

			@java.lang.Override
			public it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch getDefaultInstanceForType() {
				return it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch.getDefaultInstance();
			}

			@java.lang.Override
			public it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch build() {
				it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch result = buildPartial();
				if (!result.isInitialized()) {
					throw newUninitializedMessageException(result);
				}
				return result;
			}

			@java.lang.Override
			public it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch buildPartial() {
				it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch result = new it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch(
						this);
				int from_bitField0_ = bitField0_;
				if (keysBuilder_ == null) {
					if (((bitField0_ & 0x00000001) != 0)) {
						keys_ = java.util.Collections.unmodifiableList(keys_);
						bitField0_ = (bitField0_ & ~0x00000001);
					}
					result.keys_ = keys_;
				} else {
					result.keys_ = keysBuilder_.build();
				}
				onBuilt();
				return result;
			}

			@java.lang.Override
			public Builder clone() {
				return super.clone();
			}

			@java.lang.Override
			public Builder setField(com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
				return super.setField(field, value);
			}

			@java.lang.Override
			public Builder clearField(com.google.protobuf.Descriptors.FieldDescriptor field) {
				return super.clearField(field);
			}

			@java.lang.Override
			public Builder clearOneof(com.google.protobuf.Descriptors.OneofDescriptor oneof) {
				return super.clearOneof(oneof);
			}

			@java.lang.Override
			public Builder setRepeatedField(com.google.protobuf.Descriptors.FieldDescriptor field, int index,
					java.lang.Object value) {
				return super.setRepeatedField(field, index, value);
			}

			@java.lang.Override
			public Builder addRepeatedField(com.google.protobuf.Descriptors.FieldDescriptor field,
					java.lang.Object value) {
				return super.addRepeatedField(field, value);
			}

			@java.lang.Override
			public Builder mergeFrom(com.google.protobuf.Message other) {
				if (other instanceof it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch) {
					return mergeFrom((it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch) other);
				} else {
					super.mergeFrom(other);
					return this;
				}
			}

			public Builder mergeFrom(it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch other) {
				if (other == it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch.getDefaultInstance())
					return this;
				if (keysBuilder_ == null) {
					if (!other.keys_.isEmpty()) {
						if (keys_.isEmpty()) {
							keys_ = other.keys_;
							bitField0_ = (bitField0_ & ~0x00000001);
						} else {
							ensureKeysIsMutable();
							keys_.addAll(other.keys_);
						}
						onChanged();
					}
				} else {
					if (!other.keys_.isEmpty()) {
						if (keysBuilder_.isEmpty()) {
							keysBuilder_.dispose();
							keysBuilder_ = null;
							keys_ = other.keys_;
							bitField0_ = (bitField0_ & ~0x00000001);
							keysBuilder_ = com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
									? getKeysFieldBuilder()
									: null;
						} else {
							keysBuilder_.addAllMessages(other.keys_);
						}
					}
				}
				this.mergeUnknownFields(other.unknownFields);
				onChanged();
				return this;
			}

			@java.lang.Override
			public final boolean isInitialized() {
				return true;
			}

			@java.lang.Override
			public Builder mergeFrom(com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
				it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch parsedMessage = null;
				try {
					parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
				} catch (com.google.protobuf.InvalidProtocolBufferException e) {
					parsedMessage = (it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch) e
							.getUnfinishedMessage();
					throw e.unwrapIOException();
				} finally {
					if (parsedMessage != null) {
						mergeFrom(parsedMessage);
					}
				}
				return this;
			}

			private int bitField0_;

			private java.util.List<it.interop.federationgateway.model.EfgsProto.DiagnosisKey> keys_ = java.util.Collections
					.emptyList();

			private void ensureKeysIsMutable() {
				if (!((bitField0_ & 0x00000001) != 0)) {
					keys_ = new java.util.ArrayList<it.interop.federationgateway.model.EfgsProto.DiagnosisKey>(keys_);
					bitField0_ |= 0x00000001;
				}
			}

			private com.google.protobuf.RepeatedFieldBuilderV3<it.interop.federationgateway.model.EfgsProto.DiagnosisKey, it.interop.federationgateway.model.EfgsProto.DiagnosisKey.Builder, it.interop.federationgateway.model.EfgsProto.DiagnosisKeyOrBuilder> keysBuilder_;

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public java.util.List<it.interop.federationgateway.model.EfgsProto.DiagnosisKey> getKeysList() {
				if (keysBuilder_ == null) {
					return java.util.Collections.unmodifiableList(keys_);
				} else {
					return keysBuilder_.getMessageList();
				}
			}

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public int getKeysCount() {
				if (keysBuilder_ == null) {
					return keys_.size();
				} else {
					return keysBuilder_.getCount();
				}
			}

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public it.interop.federationgateway.model.EfgsProto.DiagnosisKey getKeys(int index) {
				if (keysBuilder_ == null) {
					return keys_.get(index);
				} else {
					return keysBuilder_.getMessage(index);
				}
			}

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public Builder setKeys(int index, it.interop.federationgateway.model.EfgsProto.DiagnosisKey value) {
				if (keysBuilder_ == null) {
					if (value == null) {
						throw new NullPointerException();
					}
					ensureKeysIsMutable();
					keys_.set(index, value);
					onChanged();
				} else {
					keysBuilder_.setMessage(index, value);
				}
				return this;
			}

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public Builder setKeys(int index,
					it.interop.federationgateway.model.EfgsProto.DiagnosisKey.Builder builderForValue) {
				if (keysBuilder_ == null) {
					ensureKeysIsMutable();
					keys_.set(index, builderForValue.build());
					onChanged();
				} else {
					keysBuilder_.setMessage(index, builderForValue.build());
				}
				return this;
			}

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public Builder addKeys(it.interop.federationgateway.model.EfgsProto.DiagnosisKey value) {
				if (keysBuilder_ == null) {
					if (value == null) {
						throw new NullPointerException();
					}
					ensureKeysIsMutable();
					keys_.add(value);
					onChanged();
				} else {
					keysBuilder_.addMessage(value);
				}
				return this;
			}

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public Builder addKeys(int index, it.interop.federationgateway.model.EfgsProto.DiagnosisKey value) {
				if (keysBuilder_ == null) {
					if (value == null) {
						throw new NullPointerException();
					}
					ensureKeysIsMutable();
					keys_.add(index, value);
					onChanged();
				} else {
					keysBuilder_.addMessage(index, value);
				}
				return this;
			}

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public Builder addKeys(it.interop.federationgateway.model.EfgsProto.DiagnosisKey.Builder builderForValue) {
				if (keysBuilder_ == null) {
					ensureKeysIsMutable();
					keys_.add(builderForValue.build());
					onChanged();
				} else {
					keysBuilder_.addMessage(builderForValue.build());
				}
				return this;
			}

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public Builder addKeys(int index,
					it.interop.federationgateway.model.EfgsProto.DiagnosisKey.Builder builderForValue) {
				if (keysBuilder_ == null) {
					ensureKeysIsMutable();
					keys_.add(index, builderForValue.build());
					onChanged();
				} else {
					keysBuilder_.addMessage(index, builderForValue.build());
				}
				return this;
			}

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public Builder addAllKeys(
					java.lang.Iterable<? extends it.interop.federationgateway.model.EfgsProto.DiagnosisKey> values) {
				if (keysBuilder_ == null) {
					ensureKeysIsMutable();
					com.google.protobuf.AbstractMessageLite.Builder.addAll(values, keys_);
					onChanged();
				} else {
					keysBuilder_.addAllMessages(values);
				}
				return this;
			}

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public Builder clearKeys() {
				if (keysBuilder_ == null) {
					keys_ = java.util.Collections.emptyList();
					bitField0_ = (bitField0_ & ~0x00000001);
					onChanged();
				} else {
					keysBuilder_.clear();
				}
				return this;
			}

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public Builder removeKeys(int index) {
				if (keysBuilder_ == null) {
					ensureKeysIsMutable();
					keys_.remove(index);
					onChanged();
				} else {
					keysBuilder_.remove(index);
				}
				return this;
			}

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public it.interop.federationgateway.model.EfgsProto.DiagnosisKey.Builder getKeysBuilder(int index) {
				return getKeysFieldBuilder().getBuilder(index);
			}

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public it.interop.federationgateway.model.EfgsProto.DiagnosisKeyOrBuilder getKeysOrBuilder(int index) {
				if (keysBuilder_ == null) {
					return keys_.get(index);
				} else {
					return keysBuilder_.getMessageOrBuilder(index);
				}
			}

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public java.util.List<? extends it.interop.federationgateway.model.EfgsProto.DiagnosisKeyOrBuilder> getKeysOrBuilderList() {
				if (keysBuilder_ != null) {
					return keysBuilder_.getMessageOrBuilderList();
				} else {
					return java.util.Collections.unmodifiableList(keys_);
				}
			}

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public it.interop.federationgateway.model.EfgsProto.DiagnosisKey.Builder addKeysBuilder() {
				return getKeysFieldBuilder()
						.addBuilder(it.interop.federationgateway.model.EfgsProto.DiagnosisKey.getDefaultInstance());
			}

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public it.interop.federationgateway.model.EfgsProto.DiagnosisKey.Builder addKeysBuilder(int index) {
				return getKeysFieldBuilder().addBuilder(index,
						it.interop.federationgateway.model.EfgsProto.DiagnosisKey.getDefaultInstance());
			}

			/**
			 * <code>repeated .eu.interop.DiagnosisKey keys = 1;</code>
			 */
			public java.util.List<it.interop.federationgateway.model.EfgsProto.DiagnosisKey.Builder> getKeysBuilderList() {
				return getKeysFieldBuilder().getBuilderList();
			}

			private com.google.protobuf.RepeatedFieldBuilderV3<it.interop.federationgateway.model.EfgsProto.DiagnosisKey, it.interop.federationgateway.model.EfgsProto.DiagnosisKey.Builder, it.interop.federationgateway.model.EfgsProto.DiagnosisKeyOrBuilder> getKeysFieldBuilder() {
				if (keysBuilder_ == null) {
					keysBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<it.interop.federationgateway.model.EfgsProto.DiagnosisKey, it.interop.federationgateway.model.EfgsProto.DiagnosisKey.Builder, it.interop.federationgateway.model.EfgsProto.DiagnosisKeyOrBuilder>(
							keys_, ((bitField0_ & 0x00000001) != 0), getParentForChildren(), isClean());
					keys_ = null;
				}
				return keysBuilder_;
			}

			@java.lang.Override
			public final Builder setUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
				return super.setUnknownFields(unknownFields);
			}

			@java.lang.Override
			public final Builder mergeUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
				return super.mergeUnknownFields(unknownFields);
			}

			// @@protoc_insertion_point(builder_scope:eu.interop.DiagnosisKeyBatch)
		}

		// @@protoc_insertion_point(class_scope:eu.interop.DiagnosisKeyBatch)
		private static final it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch DEFAULT_INSTANCE;
		static {
			DEFAULT_INSTANCE = new it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch();
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch getDefaultInstance() {
			return DEFAULT_INSTANCE;
		}

		private static final com.google.protobuf.Parser<DiagnosisKeyBatch> PARSER = new com.google.protobuf.AbstractParser<DiagnosisKeyBatch>() {
			@java.lang.Override
			public DiagnosisKeyBatch parsePartialFrom(com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry)
					throws com.google.protobuf.InvalidProtocolBufferException {
				return new DiagnosisKeyBatch(input, extensionRegistry);
			}
		};

		public static com.google.protobuf.Parser<DiagnosisKeyBatch> parser() {
			return PARSER;
		}

		@java.lang.Override
		public com.google.protobuf.Parser<DiagnosisKeyBatch> getParserForType() {
			return PARSER;
		}

		@java.lang.Override
		public it.interop.federationgateway.model.EfgsProto.DiagnosisKeyBatch getDefaultInstanceForType() {
			return DEFAULT_INSTANCE;
		}

	}

	public interface DiagnosisKeyOrBuilder extends
			// @@protoc_insertion_point(interface_extends:eu.interop.DiagnosisKey)
			com.google.protobuf.MessageOrBuilder {

		/**
		 * <pre>
		 * key
		 * </pre>
		 *
		 * <code>bytes keyData = 1;</code>
		 * 
		 * @return The keyData.
		 */
		com.google.protobuf.ByteString getKeyData();

		/**
		 * <code>uint32 rollingStartIntervalNumber = 2;</code>
		 * 
		 * @return The rollingStartIntervalNumber.
		 */
		int getRollingStartIntervalNumber();

		/**
		 * <pre>
		 * number of 10-minute windows between key-rolling
		 * </pre>
		 *
		 * <code>uint32 rollingPeriod = 3;</code>
		 * 
		 * @return The rollingPeriod.
		 */
		int getRollingPeriod();

		/**
		 * <pre>
		 * risk of transmission
		 * </pre>
		 *
		 * <code>int32 transmissionRiskLevel = 4;</code>
		 * 
		 * @return The transmissionRiskLevel.
		 */
		int getTransmissionRiskLevel();

		/**
		 * <code>repeated string visitedCountries = 5;</code>
		 * 
		 * @return A list containing the visitedCountries.
		 */
		java.util.List<java.lang.String> getVisitedCountriesList();

		/**
		 * <code>repeated string visitedCountries = 5;</code>
		 * 
		 * @return The count of visitedCountries.
		 */
		int getVisitedCountriesCount();

		/**
		 * <code>repeated string visitedCountries = 5;</code>
		 * 
		 * @param index The index of the element to return.
		 * @return The visitedCountries at the given index.
		 */
		java.lang.String getVisitedCountries(int index);

		/**
		 * <code>repeated string visitedCountries = 5;</code>
		 * 
		 * @param index The index of the value to return.
		 * @return The bytes of the visitedCountries at the given index.
		 */
		com.google.protobuf.ByteString getVisitedCountriesBytes(int index);

		/**
		 * <pre>
		 * country of origin
		 * </pre>
		 *
		 * <code>string origin = 6;</code>
		 * 
		 * @return The origin.
		 */
		java.lang.String getOrigin();

		/**
		 * <pre>
		 * country of origin
		 * </pre>
		 *
		 * <code>string origin = 6;</code>
		 * 
		 * @return The bytes for origin.
		 */
		com.google.protobuf.ByteString getOriginBytes();

		/**
		 * <pre>
		 * set by backend
		 * </pre>
		 *
		 * <code>.eu.interop.ReportType reportType = 7;</code>
		 * 
		 * @return The enum numeric value on the wire for reportType.
		 */
		int getReportTypeValue();

		/**
		 * <pre>
		 * set by backend
		 * </pre>
		 *
		 * <code>.eu.interop.ReportType reportType = 7;</code>
		 * 
		 * @return The reportType.
		 */
		it.interop.federationgateway.model.EfgsProto.ReportType getReportType();

		/**
		 * <code>sint32 days_since_onset_of_symptoms = 8;</code>
		 * 
		 * @return The daysSinceOnsetOfSymptoms.
		 */
		int getDaysSinceOnsetOfSymptoms();
	}

	/**
	 * Protobuf type {@code eu.interop.DiagnosisKey}
	 */
	public static final class DiagnosisKey extends com.google.protobuf.GeneratedMessageV3 implements
			// @@protoc_insertion_point(message_implements:eu.interop.DiagnosisKey)
			DiagnosisKeyOrBuilder {
		private static final long serialVersionUID = 0L;

		// Use DiagnosisKey.newBuilder() to construct.
		private DiagnosisKey(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
			super(builder);
		}

		private DiagnosisKey() {
			keyData_ = com.google.protobuf.ByteString.EMPTY;
			visitedCountries_ = com.google.protobuf.LazyStringArrayList.EMPTY;
			origin_ = "";
			reportType_ = 0;
		}

		@java.lang.Override
		@SuppressWarnings({ "unused" })
		protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
			return new DiagnosisKey();
		}

		@java.lang.Override
		public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
			return this.unknownFields;
		}

		private DiagnosisKey(com.google.protobuf.CodedInputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			this();
			if (extensionRegistry == null) {
				throw new java.lang.NullPointerException();
			}
			int mutable_bitField0_ = 0;
			com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet
					.newBuilder();
			try {
				boolean done = false;
				while (!done) {
					int tag = input.readTag();
					switch (tag) {
					case 0:
						done = true;
						break;
					case 10: {

						keyData_ = input.readBytes();
						break;
					}
					case 16: {

						rollingStartIntervalNumber_ = input.readUInt32();
						break;
					}
					case 24: {

						rollingPeriod_ = input.readUInt32();
						break;
					}
					case 32: {

						transmissionRiskLevel_ = input.readInt32();
						break;
					}
					case 42: {
						java.lang.String s = input.readStringRequireUtf8();
						if (!((mutable_bitField0_ & 0x00000001) != 0)) {
							visitedCountries_ = new com.google.protobuf.LazyStringArrayList();
							mutable_bitField0_ |= 0x00000001;
						}
						visitedCountries_.add(s);
						break;
					}
					case 50: {
						java.lang.String s = input.readStringRequireUtf8();

						origin_ = s;
						break;
					}
					case 56: {
						int rawValue = input.readEnum();

						reportType_ = rawValue;
						break;
					}
					case 64: {

						daysSinceOnsetOfSymptoms_ = input.readSInt32();
						break;
					}
					default: {
						if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
							done = true;
						}
						break;
					}
					}
				}
			} catch (com.google.protobuf.InvalidProtocolBufferException e) {
				throw e.setUnfinishedMessage(this);
			} catch (java.io.IOException e) {
				throw new com.google.protobuf.InvalidProtocolBufferException(e).setUnfinishedMessage(this);
			} finally {
				if (((mutable_bitField0_ & 0x00000001) != 0)) {
					visitedCountries_ = visitedCountries_.getUnmodifiableView();
				}
				this.unknownFields = unknownFields.build();
				makeExtensionsImmutable();
			}
		}

		public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
			return it.interop.federationgateway.model.EfgsProto.internal_static_eu_interop_DiagnosisKey_descriptor;
		}

		@java.lang.Override
		protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
			return it.interop.federationgateway.model.EfgsProto.internal_static_eu_interop_DiagnosisKey_fieldAccessorTable
					.ensureFieldAccessorsInitialized(it.interop.federationgateway.model.EfgsProto.DiagnosisKey.class,
							it.interop.federationgateway.model.EfgsProto.DiagnosisKey.Builder.class);
		}

		public static final int KEYDATA_FIELD_NUMBER = 1;
		private com.google.protobuf.ByteString keyData_;

		/**
		 * <pre>
		 * key
		 * </pre>
		 *
		 * <code>bytes keyData = 1;</code>
		 * 
		 * @return The keyData.
		 */
		@java.lang.Override
		public com.google.protobuf.ByteString getKeyData() {
			return keyData_;
		}

		public static final int ROLLINGSTARTINTERVALNUMBER_FIELD_NUMBER = 2;
		private int rollingStartIntervalNumber_;

		/**
		 * <code>uint32 rollingStartIntervalNumber = 2;</code>
		 * 
		 * @return The rollingStartIntervalNumber.
		 */
		@java.lang.Override
		public int getRollingStartIntervalNumber() {
			return rollingStartIntervalNumber_;
		}

		public static final int ROLLINGPERIOD_FIELD_NUMBER = 3;
		private int rollingPeriod_;

		/**
		 * <pre>
		 * number of 10-minute windows between key-rolling
		 * </pre>
		 *
		 * <code>uint32 rollingPeriod = 3;</code>
		 * 
		 * @return The rollingPeriod.
		 */
		@java.lang.Override
		public int getRollingPeriod() {
			return rollingPeriod_;
		}

		public static final int TRANSMISSIONRISKLEVEL_FIELD_NUMBER = 4;
		private int transmissionRiskLevel_;

		/**
		 * <pre>
		 * risk of transmission
		 * </pre>
		 *
		 * <code>int32 transmissionRiskLevel = 4;</code>
		 * 
		 * @return The transmissionRiskLevel.
		 */
		@java.lang.Override
		public int getTransmissionRiskLevel() {
			return transmissionRiskLevel_;
		}

		public static final int VISITEDCOUNTRIES_FIELD_NUMBER = 5;
		private com.google.protobuf.LazyStringList visitedCountries_;

		/**
		 * <code>repeated string visitedCountries = 5;</code>
		 * 
		 * @return A list containing the visitedCountries.
		 */
		public com.google.protobuf.ProtocolStringList getVisitedCountriesList() {
			return visitedCountries_;
		}

		/**
		 * <code>repeated string visitedCountries = 5;</code>
		 * 
		 * @return The count of visitedCountries.
		 */
		public int getVisitedCountriesCount() {
			return visitedCountries_.size();
		}

		/**
		 * <code>repeated string visitedCountries = 5;</code>
		 * 
		 * @param index The index of the element to return.
		 * @return The visitedCountries at the given index.
		 */
		public java.lang.String getVisitedCountries(int index) {
			return visitedCountries_.get(index);
		}

		/**
		 * <code>repeated string visitedCountries = 5;</code>
		 * 
		 * @param index The index of the value to return.
		 * @return The bytes of the visitedCountries at the given index.
		 */
		public com.google.protobuf.ByteString getVisitedCountriesBytes(int index) {
			return visitedCountries_.getByteString(index);
		}

		public static final int ORIGIN_FIELD_NUMBER = 6;
		private volatile java.lang.Object origin_;

		/**
		 * <pre>
		 * country of origin
		 * </pre>
		 *
		 * <code>string origin = 6;</code>
		 * 
		 * @return The origin.
		 */
		@java.lang.Override
		public java.lang.String getOrigin() {
			java.lang.Object ref = origin_;
			if (ref instanceof java.lang.String) {
				return (java.lang.String) ref;
			} else {
				com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
				java.lang.String s = bs.toStringUtf8();
				origin_ = s;
				return s;
			}
		}

		/**
		 * <pre>
		 * country of origin
		 * </pre>
		 *
		 * <code>string origin = 6;</code>
		 * 
		 * @return The bytes for origin.
		 */
		@java.lang.Override
		public com.google.protobuf.ByteString getOriginBytes() {
			java.lang.Object ref = origin_;
			if (ref instanceof java.lang.String) {
				com.google.protobuf.ByteString b = com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
				origin_ = b;
				return b;
			} else {
				return (com.google.protobuf.ByteString) ref;
			}
		}

		public static final int REPORTTYPE_FIELD_NUMBER = 7;
		private int reportType_;

		/**
		 * <pre>
		 * set by backend
		 * </pre>
		 *
		 * <code>.eu.interop.ReportType reportType = 7;</code>
		 * 
		 * @return The enum numeric value on the wire for reportType.
		 */
		@java.lang.Override
		public int getReportTypeValue() {
			return reportType_;
		}

		/**
		 * <pre>
		 * set by backend
		 * </pre>
		 *
		 * <code>.eu.interop.ReportType reportType = 7;</code>
		 * 
		 * @return The reportType.
		 */
		@java.lang.Override
		public it.interop.federationgateway.model.EfgsProto.ReportType getReportType() {
			@SuppressWarnings("deprecation")
			it.interop.federationgateway.model.EfgsProto.ReportType result = it.interop.federationgateway.model.EfgsProto.ReportType
					.valueOf(reportType_);
			return result == null ? it.interop.federationgateway.model.EfgsProto.ReportType.UNRECOGNIZED : result;
		}

		public static final int DAYS_SINCE_ONSET_OF_SYMPTOMS_FIELD_NUMBER = 8;
		private int daysSinceOnsetOfSymptoms_;

		/**
		 * <code>sint32 days_since_onset_of_symptoms = 8;</code>
		 * 
		 * @return The daysSinceOnsetOfSymptoms.
		 */
		@java.lang.Override
		public int getDaysSinceOnsetOfSymptoms() {
			return daysSinceOnsetOfSymptoms_;
		}

		private byte memoizedIsInitialized = -1;

		@java.lang.Override
		public final boolean isInitialized() {
			byte isInitialized = memoizedIsInitialized;
			if (isInitialized == 1)
				return true;
			if (isInitialized == 0)
				return false;

			memoizedIsInitialized = 1;
			return true;
		}

		@java.lang.Override
		public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
			if (!keyData_.isEmpty()) {
				output.writeBytes(1, keyData_);
			}
			if (rollingStartIntervalNumber_ != 0) {
				output.writeUInt32(2, rollingStartIntervalNumber_);
			}
			if (rollingPeriod_ != 0) {
				output.writeUInt32(3, rollingPeriod_);
			}
			if (transmissionRiskLevel_ != 0) {
				output.writeInt32(4, transmissionRiskLevel_);
			}
			for (int i = 0; i < visitedCountries_.size(); i++) {
				com.google.protobuf.GeneratedMessageV3.writeString(output, 5, visitedCountries_.getRaw(i));
			}
			if (!getOriginBytes().isEmpty()) {
				com.google.protobuf.GeneratedMessageV3.writeString(output, 6, origin_);
			}
			if (reportType_ != it.interop.federationgateway.model.EfgsProto.ReportType.UNKNOWN.getNumber()) {
				output.writeEnum(7, reportType_);
			}
			if (daysSinceOnsetOfSymptoms_ != 0) {
				output.writeSInt32(8, daysSinceOnsetOfSymptoms_);
			}
			unknownFields.writeTo(output);
		}

		@java.lang.Override
		public int getSerializedSize() {
			int size = memoizedSize;
			if (size != -1)
				return size;

			size = 0;
			if (!keyData_.isEmpty()) {
				size += com.google.protobuf.CodedOutputStream.computeBytesSize(1, keyData_);
			}
			if (rollingStartIntervalNumber_ != 0) {
				size += com.google.protobuf.CodedOutputStream.computeUInt32Size(2, rollingStartIntervalNumber_);
			}
			if (rollingPeriod_ != 0) {
				size += com.google.protobuf.CodedOutputStream.computeUInt32Size(3, rollingPeriod_);
			}
			if (transmissionRiskLevel_ != 0) {
				size += com.google.protobuf.CodedOutputStream.computeInt32Size(4, transmissionRiskLevel_);
			}
			{
				int dataSize = 0;
				for (int i = 0; i < visitedCountries_.size(); i++) {
					dataSize += computeStringSizeNoTag(visitedCountries_.getRaw(i));
				}
				size += dataSize;
				size += 1 * getVisitedCountriesList().size();
			}
			if (!getOriginBytes().isEmpty()) {
				size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, origin_);
			}
			if (reportType_ != it.interop.federationgateway.model.EfgsProto.ReportType.UNKNOWN.getNumber()) {
				size += com.google.protobuf.CodedOutputStream.computeEnumSize(7, reportType_);
			}
			if (daysSinceOnsetOfSymptoms_ != 0) {
				size += com.google.protobuf.CodedOutputStream.computeSInt32Size(8, daysSinceOnsetOfSymptoms_);
			}
			size += unknownFields.getSerializedSize();
			memoizedSize = size;
			return size;
		}

		@java.lang.Override
		public boolean equals(final java.lang.Object obj) {
			if (obj == this) {
				return true;
			}
			if (!(obj instanceof it.interop.federationgateway.model.EfgsProto.DiagnosisKey)) {
				return super.equals(obj);
			}
			it.interop.federationgateway.model.EfgsProto.DiagnosisKey other = (it.interop.federationgateway.model.EfgsProto.DiagnosisKey) obj;

			if (!getKeyData().equals(other.getKeyData()))
				return false;
			if (getRollingStartIntervalNumber() != other.getRollingStartIntervalNumber())
				return false;
			if (getRollingPeriod() != other.getRollingPeriod())
				return false;
			if (getTransmissionRiskLevel() != other.getTransmissionRiskLevel())
				return false;
			if (!getVisitedCountriesList().equals(other.getVisitedCountriesList()))
				return false;
			if (!getOrigin().equals(other.getOrigin()))
				return false;
			if (reportType_ != other.reportType_)
				return false;
			if (getDaysSinceOnsetOfSymptoms() != other.getDaysSinceOnsetOfSymptoms())
				return false;
			if (!unknownFields.equals(other.unknownFields))
				return false;
			return true;
		}

		@java.lang.Override
		public int hashCode() {
			if (memoizedHashCode != 0) {
				return memoizedHashCode;
			}
			int hash = 41;
			hash = (19 * hash) + getDescriptor().hashCode();
			hash = (37 * hash) + KEYDATA_FIELD_NUMBER;
			hash = (53 * hash) + getKeyData().hashCode();
			hash = (37 * hash) + ROLLINGSTARTINTERVALNUMBER_FIELD_NUMBER;
			hash = (53 * hash) + getRollingStartIntervalNumber();
			hash = (37 * hash) + ROLLINGPERIOD_FIELD_NUMBER;
			hash = (53 * hash) + getRollingPeriod();
			hash = (37 * hash) + TRANSMISSIONRISKLEVEL_FIELD_NUMBER;
			hash = (53 * hash) + getTransmissionRiskLevel();
			if (getVisitedCountriesCount() > 0) {
				hash = (37 * hash) + VISITEDCOUNTRIES_FIELD_NUMBER;
				hash = (53 * hash) + getVisitedCountriesList().hashCode();
			}
			hash = (37 * hash) + ORIGIN_FIELD_NUMBER;
			hash = (53 * hash) + getOrigin().hashCode();
			hash = (37 * hash) + REPORTTYPE_FIELD_NUMBER;
			hash = (53 * hash) + reportType_;
			hash = (37 * hash) + DAYS_SINCE_ONSET_OF_SYMPTOMS_FIELD_NUMBER;
			hash = (53 * hash) + getDaysSinceOnsetOfSymptoms();
			hash = (29 * hash) + unknownFields.hashCode();
			memoizedHashCode = hash;
			return hash;
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKey parseFrom(java.nio.ByteBuffer data)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKey parseFrom(java.nio.ByteBuffer data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKey parseFrom(
				com.google.protobuf.ByteString data) throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKey parseFrom(
				com.google.protobuf.ByteString data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKey parseFrom(byte[] data)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKey parseFrom(byte[] data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return PARSER.parseFrom(data, extensionRegistry);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKey parseFrom(java.io.InputStream input)
				throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKey parseFrom(java.io.InputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKey parseDelimitedFrom(
				java.io.InputStream input) throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKey parseDelimitedFrom(
				java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input,
					extensionRegistry);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKey parseFrom(
				com.google.protobuf.CodedInputStream input) throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKey parseFrom(
				com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws java.io.IOException {
			return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
		}

		@java.lang.Override
		public Builder newBuilderForType() {
			return newBuilder();
		}

		public static Builder newBuilder() {
			return DEFAULT_INSTANCE.toBuilder();
		}

		public static Builder newBuilder(it.interop.federationgateway.model.EfgsProto.DiagnosisKey prototype) {
			return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
		}

		@java.lang.Override
		public Builder toBuilder() {
			return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
		}

		@java.lang.Override
		protected Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
			Builder builder = new Builder(parent);
			return builder;
		}

		/**
		 * Protobuf type {@code eu.interop.DiagnosisKey}
		 */
		public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
				// @@protoc_insertion_point(builder_implements:eu.interop.DiagnosisKey)
				it.interop.federationgateway.model.EfgsProto.DiagnosisKeyOrBuilder {
			public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
				return it.interop.federationgateway.model.EfgsProto.internal_static_eu_interop_DiagnosisKey_descriptor;
			}

			@java.lang.Override
			protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
				return it.interop.federationgateway.model.EfgsProto.internal_static_eu_interop_DiagnosisKey_fieldAccessorTable
						.ensureFieldAccessorsInitialized(
								it.interop.federationgateway.model.EfgsProto.DiagnosisKey.class,
								it.interop.federationgateway.model.EfgsProto.DiagnosisKey.Builder.class);
			}

			// Construct using
			// it.interop.federationgateway.model.EfgsProto.DiagnosisKey.newBuilder()
			private Builder() {
				maybeForceBuilderInitialization();
			}

			private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
				super(parent);
				maybeForceBuilderInitialization();
			}

			private void maybeForceBuilderInitialization() {
				if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
				}
			}

			@java.lang.Override
			public Builder clear() {
				super.clear();
				keyData_ = com.google.protobuf.ByteString.EMPTY;

				rollingStartIntervalNumber_ = 0;

				rollingPeriod_ = 0;

				transmissionRiskLevel_ = 0;

				visitedCountries_ = com.google.protobuf.LazyStringArrayList.EMPTY;
				bitField0_ = (bitField0_ & ~0x00000001);
				origin_ = "";

				reportType_ = 0;

				daysSinceOnsetOfSymptoms_ = 0;

				return this;
			}

			@java.lang.Override
			public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
				return it.interop.federationgateway.model.EfgsProto.internal_static_eu_interop_DiagnosisKey_descriptor;
			}

			@java.lang.Override
			public it.interop.federationgateway.model.EfgsProto.DiagnosisKey getDefaultInstanceForType() {
				return it.interop.federationgateway.model.EfgsProto.DiagnosisKey.getDefaultInstance();
			}

			@java.lang.Override
			public it.interop.federationgateway.model.EfgsProto.DiagnosisKey build() {
				it.interop.federationgateway.model.EfgsProto.DiagnosisKey result = buildPartial();
				if (!result.isInitialized()) {
					throw newUninitializedMessageException(result);
				}
				return result;
			}

			@java.lang.Override
			public it.interop.federationgateway.model.EfgsProto.DiagnosisKey buildPartial() {
				it.interop.federationgateway.model.EfgsProto.DiagnosisKey result = new it.interop.federationgateway.model.EfgsProto.DiagnosisKey(
						this);
				int from_bitField0_ = bitField0_;
				result.keyData_ = keyData_;
				result.rollingStartIntervalNumber_ = rollingStartIntervalNumber_;
				result.rollingPeriod_ = rollingPeriod_;
				result.transmissionRiskLevel_ = transmissionRiskLevel_;
				if (((bitField0_ & 0x00000001) != 0)) {
					visitedCountries_ = visitedCountries_.getUnmodifiableView();
					bitField0_ = (bitField0_ & ~0x00000001);
				}
				result.visitedCountries_ = visitedCountries_;
				result.origin_ = origin_;
				result.reportType_ = reportType_;
				result.daysSinceOnsetOfSymptoms_ = daysSinceOnsetOfSymptoms_;
				onBuilt();
				return result;
			}

			@java.lang.Override
			public Builder clone() {
				return super.clone();
			}

			@java.lang.Override
			public Builder setField(com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
				return super.setField(field, value);
			}

			@java.lang.Override
			public Builder clearField(com.google.protobuf.Descriptors.FieldDescriptor field) {
				return super.clearField(field);
			}

			@java.lang.Override
			public Builder clearOneof(com.google.protobuf.Descriptors.OneofDescriptor oneof) {
				return super.clearOneof(oneof);
			}

			@java.lang.Override
			public Builder setRepeatedField(com.google.protobuf.Descriptors.FieldDescriptor field, int index,
					java.lang.Object value) {
				return super.setRepeatedField(field, index, value);
			}

			@java.lang.Override
			public Builder addRepeatedField(com.google.protobuf.Descriptors.FieldDescriptor field,
					java.lang.Object value) {
				return super.addRepeatedField(field, value);
			}

			@java.lang.Override
			public Builder mergeFrom(com.google.protobuf.Message other) {
				if (other instanceof it.interop.federationgateway.model.EfgsProto.DiagnosisKey) {
					return mergeFrom((it.interop.federationgateway.model.EfgsProto.DiagnosisKey) other);
				} else {
					super.mergeFrom(other);
					return this;
				}
			}

			public Builder mergeFrom(it.interop.federationgateway.model.EfgsProto.DiagnosisKey other) {
				if (other == it.interop.federationgateway.model.EfgsProto.DiagnosisKey.getDefaultInstance())
					return this;
				if (other.getKeyData() != com.google.protobuf.ByteString.EMPTY) {
					setKeyData(other.getKeyData());
				}
				if (other.getRollingStartIntervalNumber() != 0) {
					setRollingStartIntervalNumber(other.getRollingStartIntervalNumber());
				}
				if (other.getRollingPeriod() != 0) {
					setRollingPeriod(other.getRollingPeriod());
				}
				if (other.getTransmissionRiskLevel() != 0) {
					setTransmissionRiskLevel(other.getTransmissionRiskLevel());
				}
				if (!other.visitedCountries_.isEmpty()) {
					if (visitedCountries_.isEmpty()) {
						visitedCountries_ = other.visitedCountries_;
						bitField0_ = (bitField0_ & ~0x00000001);
					} else {
						ensureVisitedCountriesIsMutable();
						visitedCountries_.addAll(other.visitedCountries_);
					}
					onChanged();
				}
				if (!other.getOrigin().isEmpty()) {
					origin_ = other.origin_;
					onChanged();
				}
				if (other.reportType_ != 0) {
					setReportTypeValue(other.getReportTypeValue());
				}
				if (other.getDaysSinceOnsetOfSymptoms() != 0) {
					setDaysSinceOnsetOfSymptoms(other.getDaysSinceOnsetOfSymptoms());
				}
				this.mergeUnknownFields(other.unknownFields);
				onChanged();
				return this;
			}

			@java.lang.Override
			public final boolean isInitialized() {
				return true;
			}

			@java.lang.Override
			public Builder mergeFrom(com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
				it.interop.federationgateway.model.EfgsProto.DiagnosisKey parsedMessage = null;
				try {
					parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
				} catch (com.google.protobuf.InvalidProtocolBufferException e) {
					parsedMessage = (it.interop.federationgateway.model.EfgsProto.DiagnosisKey) e
							.getUnfinishedMessage();
					throw e.unwrapIOException();
				} finally {
					if (parsedMessage != null) {
						mergeFrom(parsedMessage);
					}
				}
				return this;
			}

			private int bitField0_;

			private com.google.protobuf.ByteString keyData_ = com.google.protobuf.ByteString.EMPTY;

			/**
			 * <pre>
			 * key
			 * </pre>
			 *
			 * <code>bytes keyData = 1;</code>
			 * 
			 * @return The keyData.
			 */
			@java.lang.Override
			public com.google.protobuf.ByteString getKeyData() {
				return keyData_;
			}

			/**
			 * <pre>
			 * key
			 * </pre>
			 *
			 * <code>bytes keyData = 1;</code>
			 * 
			 * @param value The keyData to set.
			 * @return This builder for chaining.
			 */
			public Builder setKeyData(com.google.protobuf.ByteString value) {
				if (value == null) {
					throw new NullPointerException();
				}

				keyData_ = value;
				onChanged();
				return this;
			}

			/**
			 * <pre>
			 * key
			 * </pre>
			 *
			 * <code>bytes keyData = 1;</code>
			 * 
			 * @return This builder for chaining.
			 */
			public Builder clearKeyData() {

				keyData_ = getDefaultInstance().getKeyData();
				onChanged();
				return this;
			}

			private int rollingStartIntervalNumber_;

			/**
			 * <code>uint32 rollingStartIntervalNumber = 2;</code>
			 * 
			 * @return The rollingStartIntervalNumber.
			 */
			@java.lang.Override
			public int getRollingStartIntervalNumber() {
				return rollingStartIntervalNumber_;
			}

			/**
			 * <code>uint32 rollingStartIntervalNumber = 2;</code>
			 * 
			 * @param value The rollingStartIntervalNumber to set.
			 * @return This builder for chaining.
			 */
			public Builder setRollingStartIntervalNumber(int value) {

				rollingStartIntervalNumber_ = value;
				onChanged();
				return this;
			}

			/**
			 * <code>uint32 rollingStartIntervalNumber = 2;</code>
			 * 
			 * @return This builder for chaining.
			 */
			public Builder clearRollingStartIntervalNumber() {

				rollingStartIntervalNumber_ = 0;
				onChanged();
				return this;
			}

			private int rollingPeriod_;

			/**
			 * <pre>
			 * number of 10-minute windows between key-rolling
			 * </pre>
			 *
			 * <code>uint32 rollingPeriod = 3;</code>
			 * 
			 * @return The rollingPeriod.
			 */
			@java.lang.Override
			public int getRollingPeriod() {
				return rollingPeriod_;
			}

			/**
			 * <pre>
			 * number of 10-minute windows between key-rolling
			 * </pre>
			 *
			 * <code>uint32 rollingPeriod = 3;</code>
			 * 
			 * @param value The rollingPeriod to set.
			 * @return This builder for chaining.
			 */
			public Builder setRollingPeriod(int value) {

				rollingPeriod_ = value;
				onChanged();
				return this;
			}

			/**
			 * <pre>
			 * number of 10-minute windows between key-rolling
			 * </pre>
			 *
			 * <code>uint32 rollingPeriod = 3;</code>
			 * 
			 * @return This builder for chaining.
			 */
			public Builder clearRollingPeriod() {

				rollingPeriod_ = 0;
				onChanged();
				return this;
			}

			private int transmissionRiskLevel_;

			/**
			 * <pre>
			 * risk of transmission
			 * </pre>
			 *
			 * <code>int32 transmissionRiskLevel = 4;</code>
			 * 
			 * @return The transmissionRiskLevel.
			 */
			@java.lang.Override
			public int getTransmissionRiskLevel() {
				return transmissionRiskLevel_;
			}

			/**
			 * <pre>
			 * risk of transmission
			 * </pre>
			 *
			 * <code>int32 transmissionRiskLevel = 4;</code>
			 * 
			 * @param value The transmissionRiskLevel to set.
			 * @return This builder for chaining.
			 */
			public Builder setTransmissionRiskLevel(int value) {

				transmissionRiskLevel_ = value;
				onChanged();
				return this;
			}

			/**
			 * <pre>
			 * risk of transmission
			 * </pre>
			 *
			 * <code>int32 transmissionRiskLevel = 4;</code>
			 * 
			 * @return This builder for chaining.
			 */
			public Builder clearTransmissionRiskLevel() {

				transmissionRiskLevel_ = 0;
				onChanged();
				return this;
			}

			private com.google.protobuf.LazyStringList visitedCountries_ = com.google.protobuf.LazyStringArrayList.EMPTY;

			private void ensureVisitedCountriesIsMutable() {
				if (!((bitField0_ & 0x00000001) != 0)) {
					visitedCountries_ = new com.google.protobuf.LazyStringArrayList(visitedCountries_);
					bitField0_ |= 0x00000001;
				}
			}

			/**
			 * <code>repeated string visitedCountries = 5;</code>
			 * 
			 * @return A list containing the visitedCountries.
			 */
			public com.google.protobuf.ProtocolStringList getVisitedCountriesList() {
				return visitedCountries_.getUnmodifiableView();
			}

			/**
			 * <code>repeated string visitedCountries = 5;</code>
			 * 
			 * @return The count of visitedCountries.
			 */
			public int getVisitedCountriesCount() {
				return visitedCountries_.size();
			}

			/**
			 * <code>repeated string visitedCountries = 5;</code>
			 * 
			 * @param index The index of the element to return.
			 * @return The visitedCountries at the given index.
			 */
			public java.lang.String getVisitedCountries(int index) {
				return visitedCountries_.get(index);
			}

			/**
			 * <code>repeated string visitedCountries = 5;</code>
			 * 
			 * @param index The index of the value to return.
			 * @return The bytes of the visitedCountries at the given index.
			 */
			public com.google.protobuf.ByteString getVisitedCountriesBytes(int index) {
				return visitedCountries_.getByteString(index);
			}

			/**
			 * <code>repeated string visitedCountries = 5;</code>
			 * 
			 * @param index The index to set the value at.
			 * @param value The visitedCountries to set.
			 * @return This builder for chaining.
			 */
			public Builder setVisitedCountries(int index, java.lang.String value) {
				if (value == null) {
					throw new NullPointerException();
				}
				ensureVisitedCountriesIsMutable();
				visitedCountries_.set(index, value);
				onChanged();
				return this;
			}

			/**
			 * <code>repeated string visitedCountries = 5;</code>
			 * 
			 * @param value The visitedCountries to add.
			 * @return This builder for chaining.
			 */
			public Builder addVisitedCountries(java.lang.String value) {
				if (value == null) {
					throw new NullPointerException();
				}
				ensureVisitedCountriesIsMutable();
				visitedCountries_.add(value);
				onChanged();
				return this;
			}

			/**
			 * <code>repeated string visitedCountries = 5;</code>
			 * 
			 * @param values The visitedCountries to add.
			 * @return This builder for chaining.
			 */
			public Builder addAllVisitedCountries(java.lang.Iterable<java.lang.String> values) {
				ensureVisitedCountriesIsMutable();
				com.google.protobuf.AbstractMessageLite.Builder.addAll(values, visitedCountries_);
				onChanged();
				return this;
			}

			/**
			 * <code>repeated string visitedCountries = 5;</code>
			 * 
			 * @return This builder for chaining.
			 */
			public Builder clearVisitedCountries() {
				visitedCountries_ = com.google.protobuf.LazyStringArrayList.EMPTY;
				bitField0_ = (bitField0_ & ~0x00000001);
				onChanged();
				return this;
			}

			/**
			 * <code>repeated string visitedCountries = 5;</code>
			 * 
			 * @param value The bytes of the visitedCountries to add.
			 * @return This builder for chaining.
			 */
			public Builder addVisitedCountriesBytes(com.google.protobuf.ByteString value) {
				if (value == null) {
					throw new NullPointerException();
				}
				checkByteStringIsUtf8(value);
				ensureVisitedCountriesIsMutable();
				visitedCountries_.add(value);
				onChanged();
				return this;
			}

			private java.lang.Object origin_ = "";

			/**
			 * <pre>
			 * country of origin
			 * </pre>
			 *
			 * <code>string origin = 6;</code>
			 * 
			 * @return The origin.
			 */
			public java.lang.String getOrigin() {
				java.lang.Object ref = origin_;
				if (!(ref instanceof java.lang.String)) {
					com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
					java.lang.String s = bs.toStringUtf8();
					origin_ = s;
					return s;
				} else {
					return (java.lang.String) ref;
				}
			}

			/**
			 * <pre>
			 * country of origin
			 * </pre>
			 *
			 * <code>string origin = 6;</code>
			 * 
			 * @return The bytes for origin.
			 */
			public com.google.protobuf.ByteString getOriginBytes() {
				java.lang.Object ref = origin_;
				if (ref instanceof String) {
					com.google.protobuf.ByteString b = com.google.protobuf.ByteString
							.copyFromUtf8((java.lang.String) ref);
					origin_ = b;
					return b;
				} else {
					return (com.google.protobuf.ByteString) ref;
				}
			}

			/**
			 * <pre>
			 * country of origin
			 * </pre>
			 *
			 * <code>string origin = 6;</code>
			 * 
			 * @param value The origin to set.
			 * @return This builder for chaining.
			 */
			public Builder setOrigin(java.lang.String value) {
				if (value == null) {
					throw new NullPointerException();
				}

				origin_ = value;
				onChanged();
				return this;
			}

			/**
			 * <pre>
			 * country of origin
			 * </pre>
			 *
			 * <code>string origin = 6;</code>
			 * 
			 * @return This builder for chaining.
			 */
			public Builder clearOrigin() {

				origin_ = getDefaultInstance().getOrigin();
				onChanged();
				return this;
			}

			/**
			 * <pre>
			 * country of origin
			 * </pre>
			 *
			 * <code>string origin = 6;</code>
			 * 
			 * @param value The bytes for origin to set.
			 * @return This builder for chaining.
			 */
			public Builder setOriginBytes(com.google.protobuf.ByteString value) {
				if (value == null) {
					throw new NullPointerException();
				}
				checkByteStringIsUtf8(value);

				origin_ = value;
				onChanged();
				return this;
			}

			private int reportType_ = 0;

			/**
			 * <pre>
			 * set by backend
			 * </pre>
			 *
			 * <code>.eu.interop.ReportType reportType = 7;</code>
			 * 
			 * @return The enum numeric value on the wire for reportType.
			 */
			@java.lang.Override
			public int getReportTypeValue() {
				return reportType_;
			}

			/**
			 * <pre>
			 * set by backend
			 * </pre>
			 *
			 * <code>.eu.interop.ReportType reportType = 7;</code>
			 * 
			 * @param value The enum numeric value on the wire for reportType to set.
			 * @return This builder for chaining.
			 */
			public Builder setReportTypeValue(int value) {

				reportType_ = value;
				onChanged();
				return this;
			}

			/**
			 * <pre>
			 * set by backend
			 * </pre>
			 *
			 * <code>.eu.interop.ReportType reportType = 7;</code>
			 * 
			 * @return The reportType.
			 */
			@java.lang.Override
			public it.interop.federationgateway.model.EfgsProto.ReportType getReportType() {
				@SuppressWarnings("deprecation")
				it.interop.federationgateway.model.EfgsProto.ReportType result = it.interop.federationgateway.model.EfgsProto.ReportType
						.valueOf(reportType_);
				return result == null ? it.interop.federationgateway.model.EfgsProto.ReportType.UNRECOGNIZED : result;
			}

			/**
			 * <pre>
			 * set by backend
			 * </pre>
			 *
			 * <code>.eu.interop.ReportType reportType = 7;</code>
			 * 
			 * @param value The reportType to set.
			 * @return This builder for chaining.
			 */
			public Builder setReportType(it.interop.federationgateway.model.EfgsProto.ReportType value) {
				if (value == null) {
					throw new NullPointerException();
				}

				reportType_ = value.getNumber();
				onChanged();
				return this;
			}

			/**
			 * <pre>
			 * set by backend
			 * </pre>
			 *
			 * <code>.eu.interop.ReportType reportType = 7;</code>
			 * 
			 * @return This builder for chaining.
			 */
			public Builder clearReportType() {

				reportType_ = 0;
				onChanged();
				return this;
			}

			private int daysSinceOnsetOfSymptoms_;

			/**
			 * <code>sint32 days_since_onset_of_symptoms = 8;</code>
			 * 
			 * @return The daysSinceOnsetOfSymptoms.
			 */
			@java.lang.Override
			public int getDaysSinceOnsetOfSymptoms() {
				return daysSinceOnsetOfSymptoms_;
			}

			/**
			 * <code>sint32 days_since_onset_of_symptoms = 8;</code>
			 * 
			 * @param value The daysSinceOnsetOfSymptoms to set.
			 * @return This builder for chaining.
			 */
			public Builder setDaysSinceOnsetOfSymptoms(int value) {

				daysSinceOnsetOfSymptoms_ = value;
				onChanged();
				return this;
			}

			/**
			 * <code>sint32 days_since_onset_of_symptoms = 8;</code>
			 * 
			 * @return This builder for chaining.
			 */
			public Builder clearDaysSinceOnsetOfSymptoms() {

				daysSinceOnsetOfSymptoms_ = 0;
				onChanged();
				return this;
			}

			@java.lang.Override
			public final Builder setUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
				return super.setUnknownFields(unknownFields);
			}

			@java.lang.Override
			public final Builder mergeUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
				return super.mergeUnknownFields(unknownFields);
			}

			// @@protoc_insertion_point(builder_scope:eu.interop.DiagnosisKey)
		}

		// @@protoc_insertion_point(class_scope:eu.interop.DiagnosisKey)
		private static final it.interop.federationgateway.model.EfgsProto.DiagnosisKey DEFAULT_INSTANCE;
		static {
			DEFAULT_INSTANCE = new it.interop.federationgateway.model.EfgsProto.DiagnosisKey();
		}

		public static it.interop.federationgateway.model.EfgsProto.DiagnosisKey getDefaultInstance() {
			return DEFAULT_INSTANCE;
		}

		private static final com.google.protobuf.Parser<DiagnosisKey> PARSER = new com.google.protobuf.AbstractParser<DiagnosisKey>() {
			@java.lang.Override
			public DiagnosisKey parsePartialFrom(com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry)
					throws com.google.protobuf.InvalidProtocolBufferException {
				return new DiagnosisKey(input, extensionRegistry);
			}
		};

		public static com.google.protobuf.Parser<DiagnosisKey> parser() {
			return PARSER;
		}

		@java.lang.Override
		public com.google.protobuf.Parser<DiagnosisKey> getParserForType() {
			return PARSER;
		}

		@java.lang.Override
		public it.interop.federationgateway.model.EfgsProto.DiagnosisKey getDefaultInstanceForType() {
			return DEFAULT_INSTANCE;
		}

	}

	private static final com.google.protobuf.Descriptors.Descriptor internal_static_eu_interop_DiagnosisKeyBatch_descriptor;
	private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable internal_static_eu_interop_DiagnosisKeyBatch_fieldAccessorTable;
	private static final com.google.protobuf.Descriptors.Descriptor internal_static_eu_interop_DiagnosisKey_descriptor;
	private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable internal_static_eu_interop_DiagnosisKey_fieldAccessorTable;

	public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
		return descriptor;
	}

	private static com.google.protobuf.Descriptors.FileDescriptor descriptor;
	static {
		java.lang.String[] descriptorData = { "\n\nEfgs.proto\022\neu.interop\";\n\021DiagnosisKey"
				+ "Batch\022&\n\004keys\030\001 \003(\0132\030.eu.interop.Diagnos"
				+ "isKey\"\365\001\n\014DiagnosisKey\022\017\n\007keyData\030\001 \001(\014\022"
				+ "\"\n\032rollingStartIntervalNumber\030\002 \001(\r\022\025\n\rr"
				+ "ollingPeriod\030\003 \001(\r\022\035\n\025transmissionRiskLe"
				+ "vel\030\004 \001(\005\022\030\n\020visitedCountries\030\005 \003(\t\022\016\n\006o"
				+ "rigin\030\006 \001(\t\022*\n\nreportType\030\007 \001(\0162\026.eu.int"
				+ "erop.ReportType\022$\n\034days_since_onset_of_s"
				+ "ymptoms\030\010 \001(\021*|\n\nReportType\022\013\n\007UNKNOWN\020\000"
				+ "\022\022\n\016CONFIRMED_TEST\020\001\022 \n\034CONFIRMED_CLINIC"
				+ "AL_DIAGNOSIS\020\002\022\017\n\013SELF_REPORT\020\003\022\r\n\tRECUR"
				+ "SIVE\020\004\022\013\n\007REVOKED\020\005B/\n\"eu.interop.federa"
				+ "tiongateway.modelB\tEfgsProtob\006proto3" };
		descriptor = com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData,
				new com.google.protobuf.Descriptors.FileDescriptor[] {});
		internal_static_eu_interop_DiagnosisKeyBatch_descriptor = getDescriptor().getMessageTypes().get(0);
		internal_static_eu_interop_DiagnosisKeyBatch_fieldAccessorTable = new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
				internal_static_eu_interop_DiagnosisKeyBatch_descriptor, new java.lang.String[] { "Keys", });
		internal_static_eu_interop_DiagnosisKey_descriptor = getDescriptor().getMessageTypes().get(1);
		internal_static_eu_interop_DiagnosisKey_fieldAccessorTable = new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
				internal_static_eu_interop_DiagnosisKey_descriptor,
				new java.lang.String[] { "KeyData", "RollingStartIntervalNumber", "RollingPeriod",
						"TransmissionRiskLevel", "VisitedCountries", "Origin", "ReportType",
						"DaysSinceOnsetOfSymptoms", });
	}

	// @@protoc_insertion_point(outer_class_scope)
}
