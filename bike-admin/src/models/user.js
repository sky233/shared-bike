import {ReactiveModel} from 'vue-rawmodel';

export class User extends ReactiveModel {
  constructor(data = {}) {
    super(data);
    this.defineField('username', {
      type: 'String',
      validate: [
        {
          validator: 'presence',
          message: 'is required'
        }
      ],
    });
    this.defineField('password', {
      type: 'String',
      validate: [
        {
          validator: 'presence',
          message: 'is required'
        }
      ],
    });
    this.populate(data);
  }
}
