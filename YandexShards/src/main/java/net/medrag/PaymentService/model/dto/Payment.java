package net.medrag.PaymentService.model.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Payment
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-12-12T03:56:05.857662200+03:00[Europe/Moscow]")

public class Payment   {
  @JsonProperty("sender")
  private Long sender;

  @JsonProperty("recipient")
  private Long recipient;

  @JsonProperty("amount")
  private Integer amount;

  public Payment sender(Long sender) {
    this.sender = sender;
    return this;
  }

  /**
   * Get sender
   * minimum: 1
   * @return sender
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

@Min(1L)
  public Long getSender() {
    return sender;
  }

  public void setSender(Long sender) {
    this.sender = sender;
  }

  public Payment recipient(Long recipient) {
    this.recipient = recipient;
    return this;
  }

  /**
   * Get recipient
   * minimum: 1
   * @return recipient
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

@Min(1L)
  public Long getRecipient() {
    return recipient;
  }

  public void setRecipient(Long recipient) {
    this.recipient = recipient;
  }

  public Payment amount(Integer amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * minimum: 1
   * @return amount
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

@Min(1)
  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Payment payment = (Payment) o;
    return Objects.equals(this.sender, payment.sender) &&
        Objects.equals(this.recipient, payment.recipient) &&
        Objects.equals(this.amount, payment.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sender, recipient, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Payment {\n");
    
    sb.append("    sender: ").append(toIndentedString(sender)).append("\n");
    sb.append("    recipient: ").append(toIndentedString(recipient)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

