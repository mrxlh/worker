import React, { PureComponent, Fragment } from 'react';
import { connect } from 'dva';
import moment from 'moment';
import router from 'umi/router';
import {
  Row,
  Col,
  Card,
  Form,
  Input,
  Select,
  Button,
  DatePicker,
  message,
} from 'antd';
import StandardTable from '@/components/StandardTable';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

import styles from './TableList.less';

const FormItem = Form.Item;
const { RangePicker } = DatePicker;
const { TextArea } = Input;
const { Option } = Select;
const getValue = obj =>
  Object.keys(obj)
    .map(key => obj[key])
    .join(',');
/* eslint react/no-multi-comp:0 */
@connect(({ user, loading }) => ({
  user,
  loading: loading.models.user,
}))
@Form.create()
class TableList extends PureComponent {
  state = {
    formValues: {},
  };

  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: 'user/fetchWokerNameAndStatus',
    });
  }

  handleStandardTableChange = (pagination, filtersArg, sorter) => {
    const { dispatch } = this.props;
    const { formValues } = this.state;

    const filters = Object.keys(filtersArg).reduce((obj, key) => {
      const newObj = { ...obj };
      newObj[key] = getValue(filtersArg[key]);
      return newObj;
    }, {});


    const params = {
      page: pagination.current,
      pageSize: pagination.pageSize,
      ...formValues,
      ...filters,
    };
    if (sorter.field) {
      params.sorter = `${sorter.field}_${sorter.order}`;
    }

    dispatch({
      type: 'user/fetchWokerList',
      payload: params,
    });
  };

  handleFormReset = () => {
    const { form, dispatch } = this.props;
    form.resetFields();
    this.setState({
      formValues: {},
    });
    dispatch({
      type: 'user/fetchWokerList',
      payload: {},
    });
  };

  handleSearch = e => {
    e.preventDefault();

    const { dispatch, form } = this.props;

    form.validateFields((err, fieldsValue) => {
      if (err) return;
      const dateRange = fieldsValue.date && fieldsValue.date.length ? [
        fieldsValue.date[0].format("YYYY-MM-DD HH:mm:ss"),
        fieldsValue.date[1].format("YYYY-MM-DD HH:mm:ss"),
      ] : undefined;
      const values = {
        fingerprint: fieldsValue.fingerprint,
        status: fieldsValue.status,
        taskType: fieldsValue.taskType,
        startTime: dateRange[0],
        endTime: dateRange[1],
      };

      this.setState({
        formValues: values,
      });

      dispatch({
        type: 'user/fetchWokerList',
        payload: values,
      });
    });
  };

  renderAdvancedForm() {
    const {
      form: { getFieldDecorator },
      user: { workerNameAndStatus = {}},
    } = this.props;
    const { task_type = [], worker_status = [] } = workerNameAndStatus;
    return (
      <Form onSubmit={this.handleSearch} layout="inline">
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <FormItem label="任务类型">
              {getFieldDecorator('taskType', {
                initialValue: task_type && task_type.length ? task_type[0].code : '',
              })(
                <Select>
                  {task_type.map((item) => {
                    return (
                      <Option value={item.code} key={item.code}>
                        {item.name}
                      </Option>
                    );
                  })}
                </Select>
              )}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="状态">
              {getFieldDecorator('status')(
                 <Select allowClear>
                  {worker_status.map((item) => {
                    return (
                      <Option value={item.code} key={item.code}>
                        {item.name}
                      </Option>
                    );
                  })}
                </Select>
              )}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            <FormItem label="指纹">
              {getFieldDecorator('fingerprint')(<Input style={{ width: '100%' }} />)}
            </FormItem>
          </Col>
        </Row>
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={12} sm={24}>
            <FormItem label="起止日期">
              {getFieldDecorator('date', {
                initialValue: [moment(moment().format('YYYY-MM-DD 00:00:00')), moment(moment().format('YYYY-MM-DD 23:59:59'))]
              })(
                <RangePicker
                  showTime={{
                    defaultValue: [moment('00:00:00', 'HH:mm:ss'), moment('23:59:59', 'HH:mm:ss')],
                  }}
                  style={{ width: '100%' }}
                />
              )}
            </FormItem>
          </Col>
        </Row>
        <div style={{ overflow: 'hidden' }}>
          <div style={{ marginBottom: 24 }}>
            <Button type="primary" htmlType="submit">
              查询
            </Button>
            <Button style={{ marginLeft: 8 }} onClick={this.handleFormReset}>
              重置
            </Button>
          </div>
        </div>
      </Form>
    );
  }

  // 重置
  reset = (row) => {
    const { dispatch } = this.props;
    //task_type=write_back_sol&fingerprint=20618&status=0&execute_count=0
    dispatch({
      type: 'user/workerResetTask',
      payload: {
        task_type: row.taskType,
        fingerprint:row.fingerprint,
        status:0,
        execute_count:0
      },
      callback: () => {
        const { dispatch } = this.props;
        const { formValues } = this.state;
        const params = {
          ...formValues,
        };
        dispatch({
          type: 'user/fetchWokerList',
          payload: params,
        });
        message.success('重置成功！');
      },
    });
  };
  
  // 手动执行
  executeTask = (row) => {
    // task_type=write_back_sol&fingerprint=3
    const { dispatch } = this.props;
    dispatch({
      type: 'user/workerRxecuteTask',
      payload: {
        task_type: row.taskType,
        fingerprint:row.fingerprint
      },
      callback: () => {
        const { dispatch } = this.props;
        const { formValues } = this.state;
        const params = {
          ...formValues,
        };
        dispatch({
          type: 'user/fetchWokerList',
          payload: params,
        });
       message.success(' 手动执行成功');
      },
    });
  };

  render() {
    const { loading, user } = this.props;
    const { worker = {} } = user;
    const { total = 0 } = worker;
    const tableData = Object.assign({}, worker, {
      pagination: {
        total,
      },
    });
    const {
      user: { workerNameAndStatus = {}},
    } = this.props;
    const { task_type = [], worker_status = [] } = workerNameAndStatus;
    const columns = [
      {
        title: 'ID',
        dataIndex: 'id',
        fixed: 'left',
      },
      {
        title: '任务类型',
        dataIndex: 'taskType',
      },
      {
        title: '指纹',
        dataIndex: 'fingerprint',
      },
      {
        title: '任务内容',
        dataIndex: 'taskBody',
        render(val) {
          return (
            <TextArea style={{ cursor: 'pointer' }} defaultValue={val} />
          );
        },
      },
      {
        title: '状态',
        dataIndex: 'status',
        width: '5%',
        render: (val) => {
          const statusObj = worker_status.filter(v => {return v.code == val});
          return statusObj.length ? statusObj[0].name : '';
        },
      },
      {
        title: '失败次数',
        dataIndex: 'executeCount',
      },
      {
        title: '创建时间',
        dataIndex: 'createTime',
        render: (val) => {
          return moment(val).format('YYYY-MM-DD HH:mm:ss');
        },
      },
      {
        title: '最后执行时间',
        dataIndex: 'lastTime',
        render: (val) => {
          return moment(val).format('YYYY-MM-DD HH:mm:ss');
        },
      },
      {
        title: '异常',
        dataIndex: 'remark',
        width: '15%'
      },
      {
        title: '操作',
        dataIndex: '',
        fixed: 'right',
        render: (val, row) => {
          return (
            <div>
              <a href='javascript:;' onClick={(e) => {e.preventDefault(); this.reset(row);}}>重置</a>
              <a href='javascript:;'> | </a>
              <a href='javascript:;' onClick={(e) => {e.preventDefault(); this.executeTask(row);}}>手动执行</a>
            </div>
          );
        },
      },
    ];
    return (
      <PageHeaderWrapper title="查询表格">
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListForm}>{this.renderAdvancedForm()}</div>
            <StandardTable
              selectedRows={[]}
              loading={loading}
              data={tableData}
              rowKey={'id'}
              scroll={{ x: 1300 }}
              columns={columns}
              onChange={this.handleStandardTableChange}
              rowSelection={null}
            />
          </div>
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default TableList;
